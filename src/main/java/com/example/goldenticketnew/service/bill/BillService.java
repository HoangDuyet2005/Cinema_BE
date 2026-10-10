package com.example.goldenticketnew.service.bill;

import com.example.goldenticketnew.config.cadance.CadenceWorkflowConfig;
import com.example.goldenticketnew.dtos.*;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.*;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionRequest;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionResponse;
import com.example.goldenticketnew.repository.*;
import com.example.goldenticketnew.utils.ValueComparator;
import com.example.goldenticketnew.service.payment.VnPayService;
import com.example.goldenticketnew.service.pricing.PriceCalculationService;
import com.example.goldenticketnew.workflow.interfaces.IBookingTicketWorkflow;
import com.uber.cadence.client.WorkflowClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private final IFoodItemRepository foodItemRepository;
    private final IBillFoodRepository billFoodRepository;
    private final PriceCalculationService priceCalculationService;
    private final VnPayService vnPayService;

    // Cadence là tuỳ chọn: các bean này chỉ tồn tại khi cadence.enabled=true. Inject bắt buộc qua
    // constructor khiến app không khởi động được khi tắt Cadence, dù đặt vé không cần workflow.
    @Autowired(required = false)
    private WorkflowClient workflowClient;
    @Autowired(required = false)
    private CadenceWorkflowConfig cadenceWorkflowConfig;

    public static final String workFlowId = "BOOKING_TASK_ID";
    private static final long PAYMENT_GRACE_MINUTES = 20;
    private static final int MAX_SEATS_PER_BOOKING = 10;

    @Override
    @Transactional
    public BillDto createNewBill(BookingRequestDto bookingRequestDTO) {
        Schedule schedule = scheduleRepository.findById(bookingRequestDTO.getScheduleId())
                .orElseThrow(() -> new InternalException(ResponseCode.SCHEDULE_NOT_FOUND));
        // Giữ ghế và tạo link thanh toán đã chặn đúng giờ chiếu. Ở bước này cho phép trễ tối đa
        // PAYMENT_GRACE_MINUTES vì với VNPay, hóa đơn chỉ được tạo SAU khi khách đã trả tiền
        // (link thanh toán sống 15 phút) - chặn cứng sẽ khiến khách mất tiền mà không có vé.
        if (schedule.hasStartedAt(LocalDateTime.now().minusMinutes(PAYMENT_GRACE_MINUTES))) {
            throw new InternalException(ResponseCode.SCHEDULE_ALREADY_STARTED);
        }
        User user = userRepository.findById(bookingRequestDTO.getUserId())
                .orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));

        if (bookingRequestDTO.getListSeatIds() == null || bookingRequestDTO.getListSeatIds().isEmpty()) {
            throw new InternalException(ResponseCode.SEAT_NOT_FOUND);
        }
        if (bookingRequestDTO.getListSeatIds().size() > MAX_SEATS_PER_BOOKING) {
            throw new InternalException(ResponseCode.BOOKING_SEAT_LIMIT);
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
            double seatPrice = priceCalculationService.calculateSeatPrice(schedule, seat);
            total[0] += seatPrice;
        }

        // Tạo Bill với trạng thái SUCCESS và sinh mã đặt vé duy nhất
        Bill billToCreate = new Bill();
        billToCreate.setUser(user);
        billToCreate.setCreatedTime(LocalDateTime.now());
        billToCreate.setStatus(BillStatus.SUCCESS);
        billToCreate.setPrice(total[0]);
        billToCreate.setIsCheckedIn(false);
        // SecureRandom (qua VNPayConfig.getRandomNumber) thay vì Math.random() vì bookingCode
        // dùng làm mã QR/soát vé - không nên đoán được.
        String bookingCode = "WC" + LocalDate.now().getYear() + "-" + com.example.goldenticketnew.config.VNPayConfig.getRandomNumber(6);
        billToCreate.setBookingCode(bookingCode);
        billToCreate.setQrCode(bookingCode);
        Bill createdBill = billRepository.save(billToCreate);
        bookingRequestDTO.setBillId(createdBill.getId());

        // 1. Tạo các Vé (Ticket) lưu vào bảng ticket
        for (Seat seat : seats) {
            Ticket ticket = new Ticket();
            ticket.setSchedule(schedule);
            ticket.setSeat(seat);
            ticket.setBill(createdBill);
            ticket.setQrImageURL("https://scontent-sin6-2.xx.fbcdn.net/v/t1.15752-9/268794058_655331555823095_3657556108194277679_n.png?_nc_cat=105&ccb=1-5&_nc_sid=ae9488&_nc_ohc=BrNXGO8HufkAX_OGjWc&_nc_ht=scontent-sin6-2.xx&oh=03_AVK_zaJj7pziY9nLrVqoIQJAzbomu4KPgED1PxFFpYfCrQ&oe=61F778D8");
            ticketRepository.save(ticket);
        }

        // 2. Tạo các Món Bắp Nước (BillFood) lưu vào bảng bill_food riêng biệt
        if (bookingRequestDTO.getFoods() != null && !bookingRequestDTO.getFoods().isEmpty()) {
            for (BookingFoodItemDto foodReq : bookingRequestDTO.getFoods()) {
                if (foodReq.getFoodId() != null && foodReq.getQuantity() != null && foodReq.getQuantity() > 0) {
                    FoodItem foodItem = foodItemRepository.findById(foodReq.getFoodId()).orElse(null);
                    if (foodItem != null) {
                        BillFood billFood = new BillFood();
                        billFood.setBill(createdBill);
                        billFood.setFoodItem(foodItem);
                        billFood.setQuantity(foodReq.getQuantity());
                        // Luôn lấy giá thật từ FoodItem trong DB, KHÔNG dùng foodReq.getPrice() do client tự gửi
                        // (trước đây cho phép client tự đặt giá bắp nước tuỳ ý - lỗ hổng tamper giá).
                        double itemPrice = foodItem.getPrice() != null ? foodItem.getPrice() : 0.0;
                        billFood.setPrice(itemPrice);
                        billFoodRepository.save(billFood);
                        total[0] += itemPrice * foodReq.getQuantity();
                    }
                }
            }
            // Cập nhật lại tổng giá bill bao gồm cả tiền vé + tiền bắp nước
            createdBill.setPrice(total[0]);
            billRepository.save(createdBill);
        }

        // Khách tự đặt: chỉ xuất vé khi có bằng chứng đã thanh toán. Ném lỗi ở đây sẽ rollback toàn bộ
        // bill/vé/bắp nước vừa tạo (cùng transaction của bookingHandler).
        if (bookingRequestDTO.isPaymentRequired()) {
            createdBill.setPaymentRef(verifyPaidBooking(bookingRequestDTO, total[0]));
            billRepository.save(createdBill);
        }

        return new BillDto(createdBill);
    }

    /**
     * Xác minh giao dịch VNPay đúng là đã thanh toán cho CHÍNH đơn này, trả về mã giao dịch.
     * Trước đây bill được tạo thẳng với trạng thái SUCCESS mà không cần thanh toán -> khách gọi thẳng
     * API là lấy được vé miễn phí.
     */
    private String verifyPaidBooking(BookingRequestDto dto, double total) {
        Map<String, String> proof = dto.getPaymentProof();
        if (!vnPayService.isPaidSuccessfully(proof)) {
            throw new InternalException(ResponseCode.PAYMENT_INVALID);
        }
        // Số tiền đã trả phải bằng đúng giá trị đơn do server tự tính lại (VNPay tính theo đơn vị x100)
        if (!String.valueOf(Math.round(total) * 100).equals(proof.get("vnp_Amount"))) {
            throw new InternalException(ResponseCode.PAYMENT_INVALID);
        }
        // Giao dịch phải là của đúng người, đúng suất, đúng ghế: vnp_OrderInfo = PAY_{user}_{suất}_{ghế-ghế}[_...]
        String[] parts = String.valueOf(proof.get("vnp_OrderInfo")).split("_");
        Set<String> paidSeats = parts.length > 3 ? new HashSet<>(Arrays.asList(parts[3].split("-"))) : Collections.emptySet();
        Set<String> bookedSeats = dto.getListSeatIds().stream().map(String::valueOf).collect(Collectors.toSet());
        if (parts.length < 4 || !"PAY".equals(parts[0])
                || !parts[1].equals(String.valueOf(dto.getUserId()))
                || !parts[2].equals(String.valueOf(dto.getScheduleId()))
                || !paidSeats.equals(bookedSeats)) {
            throw new InternalException(ResponseCode.PAYMENT_INVALID);
        }
        // Mỗi giao dịch chỉ được dùng xuất vé 1 lần (cột payment_ref còn có ràng buộc unique ở DB)
        String paymentRef = proof.get("vnp_TxnRef");
        if (paymentRef == null || billRepository.existsByPaymentRef(paymentRef)) {
            throw new InternalException(ResponseCode.PAYMENT_ALREADY_USED);
        }
        return paymentRef;
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
        });
        billRepository.deleteById(bill.getId());
    }

    @Override
    public BillDto payBill(Integer id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
        bill.setStatus(BillStatus.SUCCESS);
        bill = billRepository.save(bill);
        return new BillDto(bill);
    }

    @Override
    @Transactional
    public BillDto bookingHandler(BookingRequestDto bookingRequestDTO) {
        try {
            BillDto bill = createNewBill(bookingRequestDTO);
            if (workflowClient == null || cadenceWorkflowConfig == null) {
                return bill;
            }
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
        normalizeDashboardRequest(request);
        Integer status = request.getStatus().ordinal();
        Integer branchId = normalizeFilterId(request.getBranchId());
        Integer movieId = normalizeFilterId(request.getMovieId());
        List<DayTransactionReport> dayReports = billRepository.findDashboardBillByFilters(
            request.getFromDate(),
            request.getToDate(),
            status,
            branchId,
            movieId
        );
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
        } else {
            response.setTotalTicket(0L);
        }
        Double averageOccupancyRate = billRepository.findAverageOccupancyRateByFilters(
            request.getFromDate(),
            request.getToDate(),
            status,
            branchId,
            movieId
        );
        response.setAverageOccupancyRate(averageOccupancyRate == null ? 0D : averageOccupancyRate);
        response.setTopMovies(billRepository.findTopMoviesByFilters(
            request.getFromDate(),
            request.getToDate(),
            status,
            branchId,
            movieId
        ));
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
    public List<UserReportDto> getUserDashBoard(GetDashboardTransactionRequest request) {
        normalizeDashboardRequest(request);
        List<UserReportDto> reports = billRepository.findUserDashboardByFilters(
            request.getFromDate(),
            request.getToDate(),
            request.getStatus().ordinal(),
            normalizeFilterId(request.getBranchId()),
            normalizeFilterId(request.getMovieId()),
            request.getUserId() == null ? 0L : request.getUserId()
        );
        reports.sort(new ValueComparator());
        return reports;
    }

    @Override
    public BillDetailDto getBillDetail(Integer id) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
        List<Ticket> tickets = ticketRepository.findTicketsByBillId(id);
        List<BillFood> billFoods = billFoodRepository.findByBillId(id);
        BillDetailDto billDetailDto = new BillDetailDto(bill, tickets, billFoods);
        if (tickets != null && !tickets.isEmpty()) {
            for (int i = 0; i < tickets.size(); i++) {
                Ticket t = tickets.get(i);
                if (i < billDetailDto.getSeats().size() && t.getSchedule() != null && t.getSeat() != null) {
                    double seatPrice = priceCalculationService.calculateSeatPrice(t.getSchedule(), t.getSeat());
                    billDetailDto.getSeats().get(i).setPrice(seatPrice);
                }
            }
        }
        return billDetailDto;
    }

    @Override
    public List<BillDto> getList(GetDashboardTransactionRequest request) {
        if (request.getBranchId() != null && request.getBranchId() == 0) {
            request.setBranchId(null);
        }
        if (request.getMovieId() != null && request.getMovieId() == 0) {
            request.setMovieId(null);
        }
        List<Bill> listTrans = billRepository.findAll(request.getSpecification(), Sort.by(Bill.Fields.createdTime).descending());
        List<BillDto> alist = listTrans.stream().map(BillDto::new).collect(Collectors.toList());
        return alist;
    }

    private void normalizeDashboardRequest(GetDashboardTransactionRequest request) {
        if (request.getStatus() == null) {
            request.setStatus(BillStatus.SUCCESS);
        }
        if (request.getFromDate() == null || request.getFromDate().trim().isEmpty() || request.getFromDate().equalsIgnoreCase("undefined")) {
            request.setFromDate(LocalDate.now().minusDays(30).toString());
        }
        if (request.getToDate() == null || request.getToDate().trim().isEmpty() || request.getToDate().equalsIgnoreCase("undefined")) {
            request.setToDate(LocalDate.now().toString());
        }
    }

    private Integer normalizeFilterId(Integer id) {
        return id == null || id < 0 ? 0 : id;
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
            } catch (Exception ignored) {
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

        String[] showtimeProblem = showtimeProblem(bill.getId());
        if (showtimeProblem != null) {
            return new CheckTicketResponseDto(showtimeProblem[0], showtimeProblem[1], detail);
        }

        return new CheckTicketResponseDto("VALID", "Mã vé hợp lệ! Đơn đặt vé đã sẵn sàng để in vé.", detail);
    }

    /**
     * Vé chỉ được soát/in trong ngày của suất chiếu và trước khi suất kết thúc (như CGV).
     * Trả về {trạng thái, thông báo} nếu không hợp lệ, null nếu hợp lệ.
     */
    private String[] showtimeProblem(Integer billId) {
        Schedule schedule = ticketRepository.findTicketsByBillId(billId).stream()
                .map(Ticket::getSchedule).filter(Objects::nonNull).findFirst().orElse(null);
        if (schedule == null || schedule.getStartDate() == null || schedule.getStartTime() == null) {
            return null;
        }
        LocalDateTime start = LocalDateTime.of(schedule.getStartDate(), schedule.getStartTime());
        String showtime = start.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy"));
        if (schedule.getStartDate().isAfter(LocalDate.now())) {
            return new String[]{"NOT_TODAY", "Vé dành cho suất " + showtime + ", chưa đến ngày chiếu!"};
        }
        int duration = schedule.getMovie() != null ? schedule.getMovie().getDuration() : 0;
        if (LocalDateTime.now().isAfter(start.plusMinutes(duration))) {
            return new String[]{"SHOW_ENDED", "Vé dành cho suất " + showtime + ", suất chiếu đã kết thúc!"};
        }
        return null;
    }

    @Override
    @Transactional
    public BillDetailDto confirmCheckIn(Integer billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
        // Trước đây xác nhận in vé không kiểm tra gì: in được vé chưa thanh toán, in nhiều lần, sai ngày
        if (bill.getStatus() != BillStatus.SUCCESS || Boolean.TRUE.equals(bill.getIsCheckedIn())
                || showtimeProblem(billId) != null) {
            throw new InternalException(ResponseCode.CHECKIN_NOT_ALLOWED);
        }

        bill.setIsCheckedIn(true);
        bill.setCheckInTime(LocalDateTime.now());
        billRepository.save(bill);

        return getBillDetail(billId);
    }
}
