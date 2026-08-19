package com.example.goldenticketnew.service.bill;


//import com.example.goldenticketnew.config.cadance.CadenceWorkflowConfig;

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
import java.util.Comparator;
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

    private final CadenceWorkflowConfig workflowConfig;

    @Override
    @Transactional
    public BillDto createNewBill(BookingRequestDto bookingRequestDTO) {

        //Lấy ra lịch
        Schedule schedule = scheduleRepository.getById(bookingRequestDTO.getScheduleId());
        //Lấy ra người dùng
        User user = userRepository.getById(bookingRequestDTO.getUserId());
        final double[] total = {0};
        //Lưu Bill gồm thông tin người dùng xuống trước
        Bill billToCreate = new Bill();
        billToCreate.setUser(user);
        billToCreate.setCreatedTime(LocalDateTime.now());
        billToCreate.setStatus(BillStatus.WAITING_PAYMENT);
        Bill createdBill = billRepository.save(billToCreate);
        bookingRequestDTO.setBillId(createdBill.getId());
        //Với mỗi ghế ngồi check xem đã có ai đặt chưa, nếu rồi thì throw, roll back luôn còn ko
        //thì đóng gói các thông tin ghế và lịch vào vé và lưu xuống db
        bookingRequestDTO.getListSeatIds().forEach(seatId -> {
            if (!ticketRepository.findTicketsBySchedule_IdAndSeat_Id(schedule.getId(), seatId)
                .isEmpty()) {// Nếu đã có người đặt vé ghế đó ở lịch cụ thể đó thì
                throw new InternalException(ResponseCode.BOOKING_SEAT_EXIST);
            }
            // đóng gói lịch, seat và bill vào từng vé rồi add vào list vé
            Seat seat = seatRepository.findFirstById(seatId);
            if (seat == null) {
                throw new InternalException(ResponseCode.SEAT_NOT_FOUND);
            }
            if (seat.getSeatType().equals(SeatType.VIP)) {
                total[0] += schedule.getPrice() + 10000;
            } else {
                total[0] += schedule.getPrice();
            }
            Ticket ticket = new Ticket();
            ticket.setSchedule(schedule);
            ticket.setSeat(seat);
            ticket.setBill(createdBill);
            ticket.setQrImageURL("https://scontent-sin6-2.xx.fbcdn.net/v/t1.15752-9/268794058_655331555823095_3657556108194277679_n.png?_nc_cat=105&ccb=1-5&_nc_sid=ae9488&_nc_ohc=BrNXGO8HufkAX_OGjWc&_nc_ht=scontent-sin6-2.xx&oh=03_AVK_zaJj7pziY9nLrVqoIQJAzbomu4KPgED1PxFFpYfCrQ&oe=61F778D8");
            ticketRepository.save(ticket);
        });
        billToCreate.setPrice(total[0]);
        billToCreate = billRepository.save(billToCreate);
        return new BillDto(billToCreate);
    }

    @Override
    public void removeBill(DeleteBillTicketRequest request) throws RuntimeException {
        System.out.println("bat dau xoa bill");
        Bill bill = billRepository.findById(request.getBillId()).orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));

        if (bill.getStatus().equals(BillStatus.SUCCESS)) {
            throw new RuntimeException("Bill đã được thanh toán thành công");
        }
        List<Ticket> tickets = ticketRepository.findTicketsByBillId(bill.getId());
        tickets.forEach(ticket -> {
            ticketRepository.deleteById(ticket.getId());
            System.out.println("xoa thanh cong");
            bill.setStatus(BillStatus.EXPIRATION);
            billRepository.save(bill);
        });
    }

    @Override
    public BillDto payBill(Integer id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
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
    public BillDto bookingHandler(BookingRequestDto bookingRequestDTO) {
        // Get a new client
        try {
            BillDto bill = createNewBill(bookingRequestDTO);
            System.out.println("create bill");
            // Get a workflow stub using the same task list the worker uses.
//             IBookingTicketWorkflow workflow = workflowClient.newWorkflowStub(BookingTicketWorkflow.class,
            try {
                log.info("Start workflow Booking");
                IBookingTicketWorkflow workflow = workflowClient.newWorkflowStub(IBookingTicketWorkflow.class,
                    cadenceWorkflowConfig.getWorkflowOptionMap().get(CadenceWorkflowConfig.BOOKING_TASK)
                );
                WorkflowClient.start(workflow::getBooking,bookingRequestDTO, cadenceWorkflowConfig.clone());
            } catch (Exception e) {
                log.info("Workflow already run");
                log.error(e.getMessage());
            }
            return bill;
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
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
        Long totalTicket = 0l;
        Long totalIncome = 0l;
        Integer totalTransaction = 0;
        for (DayTransactionReport dayTransactionReport : dayReports) {
            totalTransaction += dayTransactionReport.getTransactionCount();
            totalIncome += dayTransactionReport.getIncomeAmount();
            if(dayTransactionReport.getTicketAmount()!=null) {
                totalTicket += dayTransactionReport.getTicketAmount();
            }
        }
        response.setTotalTransaction(totalTransaction);
        response.setTotalIncome(totalIncome);
        if(!totalTicket.equals(0l)) {
            response.setTotalTicket(totalTicket);
        }
        return response;
    }

    @Override
    public List<TransactionReportSuccess> getTranS(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTimeMY = LocalDate.parse(dateTime, formatter);
        List<TransactionReportSuccess>transactionReportSuccesses = new ArrayList<>();
        List<TranSuccess> tranSuccesses = billRepository.findAllByUserTransucess(dateTimeMY.getYear(),dateTimeMY.getMonthValue());
        for(TranSuccess tranSuccess:tranSuccesses){
            TransactionReportSuccess transactionReportSuccess = new TransactionReportSuccess(dateTime.substring(0,7),tranSuccess.getUserId(),tranSuccess.getAmountTran(),tranSuccess.getPrecentAmount());
            transactionReportSuccesses.add(transactionReportSuccess);
        }
        return transactionReportSuccesses.stream().sorted(Comparator.comparing(TransactionReportSuccess::getPrecipitation).reversed()).collect(Collectors.toList());
    }

    @Override
    public List<UserReportDto> getUserDashBoard(BillStatus status) {
        List<UserReportDto> reports = new ArrayList<>();
        if (status.equals(BillStatus.SUCCESS)) {
            reports = billRepository.findAllByStatusSuccessGroupByUser();
        } else if (status.equals(BillStatus.EXPIRATION)) {

            reports =  ModelMapperUtils.mapList(billRepository.findAllByStatusExGroupByUser(),UserReportDto.class) ;
        }
        reports.sort(new ValueComparator());
        return reports;
    }

    @Override
    public List<BillDto> getList(GetDashboardTransactionRequest request) {
        List<Bill> listTrans = billRepository.findAll(request.getSpecification(), Sort.by(Bill.Fields.createdTime).ascending());
        List<BillDto> alist = listTrans.stream().map(BillDto::new).collect(Collectors.toList());
        return alist;
    }
}
