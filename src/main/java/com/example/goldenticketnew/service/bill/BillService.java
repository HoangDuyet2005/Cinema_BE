package com.example.goldenticketnew.service.bill;

import com.example.goldenticketnew.config.cadance.CadenceWorkflowConfig;
import com.example.goldenticketnew.dtos.*;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.enums.SeatType;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.*;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionRequest;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionResponse;
import com.example.goldenticketnew.repository.*;
import com.example.goldenticketnew.utils.ModelMapperUtils;
import com.example.goldenticketnew.utils.ValueComparator;
import com.example.goldenticketnew.workflow.interfaces.IBookingTicketWorkflow;
import com.uber.cadence.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BillService implements IBillService {
    private final IScheduleRepository scheduleRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ISeatRepository seatRepository;
    private final IBillRepository billRepository;
    private final WorkflowClient workflowClient;
    private final CadenceWorkflowConfig cadenceWorkflowConfig;

    public static final String workFlowId = "BOOKING_TASK_ID";

    @Override
    @Transactional
    public BillDto createNewBill(BookingRequestDto bookingRequestDTO) {
        Schedule schedule = scheduleRepository.findById(bookingRequestDTO.getScheduleId())
                .orElseThrow(() -> new InternalException(ResponseCode.SCHEDULE_NOT_FOUND));
        User user = userRepository.findById(bookingRequestDTO.getUserId())
                .orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));

        if (bookingRequestDTO.getListSeatIds() == null || bookingRequestDTO.getListSeatIds().isEmpty()) {
            throw new InternalException(ResponseCode.SEAT_NOT_FOUND);
        }

        final double[] total = {0};
        List<Seat> seats = new ArrayList<>();
        for (Integer seatId : bookingRequestDTO.getListSeatIds()) {
            List<Ticket> existingTickets = ticketRepository.findTicketsBySchedule_IdAndSeat_Id(schedule.getId(), seatId);
            boolean isAlreadyBought = existingTickets.stream().anyMatch(t -> t.getBill() != null && t.getBill().getStatus() == BillStatus.SUCCESS);
            if (isAlreadyBought) {
                throw new InternalException(ResponseCode.BOOKING_SEAT_EXIST);
            }
            Seat seat = seatRepository.findFirstById(seatId);
            if (seat == null) {
                throw new InternalException(ResponseCode.SEAT_NOT_FOUND);
            }
            seats.add(seat);
            if (seat.getSeatType() != null && seat.getSeatType().equals(SeatType.VIP)) {
                total[0] += schedule.getPrice() + 10000;
            } else {
                total[0] += schedule.getPrice();
            }
        }

        // Tạo Bill với trạng thái SUCCESS và sinh mã đặt vé duy nhất
        Bill billToCreate = new Bill();
        billToCreate.setUser(user);
        billToCreate.setCreatedTime(LocalDateTime.now());
        billToCreate.setStatus(BillStatus.SUCCESS);
        billToCreate.setPrice(total[0]);
        billToCreate.setIsCheckedIn(false);
        String bookingCode = "WC" + LocalDate.now().getYear() + "-" + String.format("%06d", (int)(Math.random() * 900000 + 100000));
        billToCreate.setBookingCode(bookingCode);
        billToCreate.setQrCode(bookingCode);
        Bill createdBill = billRepository.save(billToCreate);
        bookingRequestDTO.setBillId(createdBill.getId());

        // Tạo các Ticket liên kết với Bill
        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setSchedule(schedule);
            ticket.setSeat(seat);
            ticket.setBill(createdBill);
            ticket.setQrImageURL("https://scontent-sin6-2.xx.fbcdn.net/v/t1.15752-9/268794058_655331555823095_3657556108194277679_n.png?_nc_cat=105&ccb=1-5&_nc_sid=ae9488&_nc_ohc=BrNXGO8HufkAX_OGjWc&_nc_ht=scontent-sin6-2.xx&oh=03_AVK_zaJj7pziY9nLrVqoIQJAzbomu4KPgED1PxFFpYfCrQ&oe=61F778D8");
            ticketRepository.save(ticket);
        }

        return new BillDto(createdBill);
    }

    @Override
    public void removeBill(DeleteBillTicketRequest request) throws RuntimeException {
        Bill bill = billRepository.findById(request.getBillId())
                .orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));

        if (bill.getStatus().equals(BillStatus.SUCCESS)) {
            throw new RuntimeException("Bill đã được thanh toán thành công");
        }
        List<Ticket> tickets = ticketRepository.findTicketsByBillId(bill.getId());
        tickets.forEach(ticket -> {
            ticketRepository.deleteById(ticket.getId());
            bill.setStatus(BillStatus.EXPIRATION);
            billRepository.save(bill);
        });
    }

    @Override
    public BillDto payBill(Integer id) {
        Bill bill = billRepository.findById(id)
                .orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
        if (bill.getStatus().equals(BillStatus.SUCCESS)) {
            throw new RuntimeException("Đã được thanh toán thành công");
        }
        if (bill.getStatus().equals(BillStatus.EXPIRATION)) {
            throw new RuntimeException("Đã het han");
        }
        bill.setStatus(BillStatus.SUCCESS);
        billRepository.save(bill);
        return new BillDto(bill);
    }

    @Override
    @Transactional
    public BillDto bookingHandler(BookingRequestDto bookingRequestDTO) {
        try {
            BillDto bill = createNewBill(bookingRequestDTO);
            try {
                log.info("Start workflow Booking");
                IBookingTicketWorkflow workflow = workflowClient.newWorkflowStub(IBookingTicketWorkflow.class,
                    cadenceWorkflowConfig.getWorkflowOptionMap().get(CadenceWorkflowConfig.BOOKING_TASK)
                );
                WorkflowClient.start(workflow::getBooking, bookingRequestDTO, cadenceWorkflowConfig.clone());
            } catch (Exception e) {
                log.info("Workflow already run");
                log.error(e.getMessage());
            }
            return bill;
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public GetDashboardTransactionResponse getDashBoardTransaction(GetDashboardTransactionRequest request) {
        List<DayTransactionReport> dayReports = new ArrayList<>();
        if (request.getStatus().equals(BillStatus.SUCCESS)) {
            dayReports = billRepository.findDashBoardBillByFromDateToDateAndStatusSuccess(request.getFromDate(), request.getToDate());
        } else if (request.getStatus().equals(BillStatus.EXPIRATION)) {
            dayReports = billRepository.findDashBoardBillByFromDateToDateAndStatusEx(request.getFromDate(), request.getToDate());
        }
        GetDashboardTransactionResponse response = new GetDashboardTransactionResponse();
        response.setDayTransactionReports(dayReports);
        Long totalTicket = 0L;
        Long totalIncome = 0L;
        Integer totalTransaction = 0;
        for (DayTransactionReport dayTransactionReport : dayReports) {
            totalTransaction += dayTransactionReport.getTransactionCount();
            totalIncome += dayTransactionReport.getIncomeAmount();
            if (dayTransactionReport.getTicketAmount() != null) {
                totalTicket += dayTransactionReport.getTicketAmount();
            }
        }
        response.setTotalTransaction(totalTransaction);
        response.setTotalIncome(totalIncome);
        if (!totalTicket.equals(0L)) {
            response.setTotalTicket(totalTicket);
        }
        return response;
    }

    @Override
    public List<TransactionReportSuccess> getTranS(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTimeMY = LocalDate.parse(dateTime, formatter);
        List<TransactionReportSuccess> transactionReportSuccesses = new ArrayList<>();
        List<TranSuccess> tranSuccesses = billRepository.findAllByUserTransucess(dateTimeMY.getYear(), dateTimeMY.getMonthValue());
        for (TranSuccess tranSuccess : tranSuccesses) {
            TransactionReportSuccess transactionReportSuccess = new TransactionReportSuccess(dateTime.substring(0, 7), tranSuccess.getUserId(), tranSuccess.getAmountTran(), tranSuccess.getPrecentAmount());
            transactionReportSuccesses.add(transactionReportSuccess);
        }
        return transactionReportSuccesses.stream().sorted((t1, t2) -> Double.compare(t2.getPrecipitation(), t1.getPrecipitation())).collect(Collectors.toList());
    }

    @Override
    public List<UserReportDto> getUserDashBoard(BillStatus status) {
        List<UserReportDto> reports = new ArrayList<>();
        if (status.equals(BillStatus.SUCCESS)) {
            reports = billRepository.findAllByStatusSuccessGroupByUser();
        } else if (status.equals(BillStatus.EXPIRATION)) {
            reports = ModelMapperUtils.mapList(billRepository.findAllByStatusExGroupByUser(), UserReportDto.class);
        }
        reports.sort(new ValueComparator());
        return reports;
    }

    @Override
    public BillDetailDto getBillDetail(Integer id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
        List<Ticket> tickets = ticketRepository.findTicketsByBillId(id);
        return new BillDetailDto(bill, tickets);
    }

    @Override
    public List<BillDto> getList(GetDashboardTransactionRequest request) {
        List<Bill> listTrans = billRepository.findAll(request.getSpecification(), Sort.by(Bill.Fields.createdTime).descending());
        List<BillDto> alist = listTrans.stream().map(BillDto::new).collect(Collectors.toList());
        return alist;
    }

    @Override
    public CheckTicketResponseDto checkTicket(String codeOrId) {
        if (codeOrId == null || codeOrId.trim().isEmpty()) {
            return new CheckTicketResponseDto("NOT_FOUND", "Vui lòng nhập hoặc quét mã đặt vé / QR Code!", null);
        }

        String searchCode = codeOrId.trim();
        Bill bill = billRepository.findByBookingCode(searchCode).orElse(null);

        if (bill == null) {
            try {
                int billId = Integer.parseInt(searchCode.replace("#", ""));
                bill = billRepository.findById(billId).orElse(null);
            } catch (NumberFormatException ignored) {
            }
        }

        if (bill == null) {
            return new CheckTicketResponseDto("NOT_FOUND", "Mã đặt vé hoặc mã QR không tồn tại trong hệ thống!", null);
        }

        BillDetailDto detail = getBillDetail(bill.getId());

        if (bill.getStatus() == BillStatus.EXPIRATION) {
            return new CheckTicketResponseDto("EXPIRED_OR_CANCELLED", "Đơn đặt vé đã bị hủy hoặc hết hiệu lực theo quy định!", detail);
        }

        if (bill.getStatus() == BillStatus.WAITING_PAYMENT) {
            return new CheckTicketResponseDto("WAITING_PAYMENT", "Đơn đặt vé chưa hoàn tất thanh toán!", detail);
        }

        if (Boolean.TRUE.equals(bill.getIsCheckedIn())) {
            String checkInStr = bill.getCheckInTime() != null
                    ? bill.getCheckInTime().format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"))
                    : "";
            return new CheckTicketResponseDto("ALREADY_CHECKED_IN", "Vé này đã được nhận / in trước đó vào lúc " + checkInStr + "!", detail);
        }

        return new CheckTicketResponseDto("VALID", "Mã vé hợp lệ! Đơn đặt vé đã sẵn sàng để in vé.", detail);
    }

    @Override
    @Transactional
    public BillDetailDto confirmCheckIn(Integer billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));

        if (bill.getStatus() != BillStatus.SUCCESS) {
            throw new RuntimeException("Đơn đặt vé chưa thanh toán hoặc không hợp lệ!");
        }

        if (Boolean.TRUE.equals(bill.getIsCheckedIn())) {
            throw new RuntimeException("Vé này đã được nhận / in trước đó!");
        }

        bill.setIsCheckedIn(true);
        bill.setCheckInTime(LocalDateTime.now());
        billRepository.save(bill);

        return getBillDetail(billId);
    }
}