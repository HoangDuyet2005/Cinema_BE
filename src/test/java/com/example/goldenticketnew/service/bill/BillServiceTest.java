package com.example.goldenticketnew.service.bill;

import com.example.goldenticketnew.config.cadance.CadenceWorkflowConfig;
import com.example.goldenticketnew.dtos.BillDto;
import com.example.goldenticketnew.dtos.BookingFoodItemDto;
import com.example.goldenticketnew.dtos.BookingRequestDto;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.enums.SeatType;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.*;
import com.example.goldenticketnew.repository.*;
import com.example.goldenticketnew.service.pricing.PriceCalculationService;
import com.uber.cadence.client.WorkflowClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceTest {

    @Mock
    private IScheduleRepository scheduleRepository;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ISeatRepository seatRepository;

    @Mock
    private IBillRepository billRepository;

    @Mock
    private IFoodItemRepository foodItemRepository;

    @Mock
    private IBillFoodRepository billFoodRepository;

    @Mock
    private PriceCalculationService priceCalculationService;

    @Mock
    private WorkflowClient workflowClient;

    @Mock
    private CadenceWorkflowConfig cadenceWorkflowConfig;

    @InjectMocks
    private BillService billService;

    private BookingRequestDto validBookingRequest;
    private Schedule mockSchedule;
    private User mockUser;
    private Seat mockSeat;
    private Bill savedBill;

    @BeforeEach
    void setUp() {
        // Tạo request đặt vé hợp lệ
        validBookingRequest = new BookingRequestDto();
        validBookingRequest.setUserId(1L);
        validBookingRequest.setScheduleId(1);
        validBookingRequest.setListSeatIds(Arrays.asList(1));

        // Tạo dữ liệu mock
        mockSchedule = new Schedule();
        mockSchedule.setId(1);
        mockSchedule.setPrice(80000.0);
        mockSchedule.setStartDate(LocalDate.of(2026, 12, 1));
        mockSchedule.setStartTime(LocalTime.of(14, 30));

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Test User");
        mockUser.setUsername("testuser");

        mockSeat = new Seat();
        mockSeat.setId(1);
        mockSeat.setName("A1");
        mockSeat.setSeatType(SeatType.NORMAL);

        savedBill = new Bill();
        savedBill.setId(1);
        savedBill.setUser(mockUser);
        savedBill.setStatus(BillStatus.SUCCESS);
        savedBill.setPrice(80000.0);
        savedBill.setBookingCode("WC2026-123456");
    }

    // ==================== UNIT TEST: createNewBill ====================

    @Test
    void createNewBill_whenValidRequest_shouldReturnBillDto() {
        // Given
        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));
        given(ticketRepository.findTicketsBySchedule_IdAndSeat_Id(1, 1)).willReturn(new ArrayList<>());
        given(seatRepository.findFirstById(1)).willReturn(mockSeat);
        given(priceCalculationService.calculateSeatPrice(any(Schedule.class), any(Seat.class))).willReturn(80000.0);
        given(billRepository.save(any(Bill.class))).willReturn(savedBill);

        // When
        BillDto result = billService.createNewBill(validBookingRequest);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(BillStatus.SUCCESS);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void createNewBill_whenScheduleNotFound_shouldThrowException() {
        // Given
        given(scheduleRepository.findById(1)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> billService.createNewBill(validBookingRequest))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void createNewBill_whenUserNotFound_shouldThrowException() {
        // Given
        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> billService.createNewBill(validBookingRequest))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void createNewBill_whenSeatListEmpty_shouldThrowException() {
        // Given
        validBookingRequest.setListSeatIds(new ArrayList<>());
        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));

        // When & Then
        assertThatThrownBy(() -> billService.createNewBill(validBookingRequest))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void createNewBill_whenSeatAlreadyBooked_shouldThrowException() {
        // Given - Ghế đã có người đặt và thanh toán thành công
        Ticket existingTicket = new Ticket();
        Bill existingBill = new Bill();
        existingBill.setStatus(BillStatus.SUCCESS);
        existingTicket.setBill(existingBill);

        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));
        given(ticketRepository.findTicketsBySchedule_IdAndSeat_Id(1, 1))
                .willReturn(Arrays.asList(existingTicket));

        // When & Then
        assertThatThrownBy(() -> billService.createNewBill(validBookingRequest))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void createNewBill_whenSeatNotFoundInDB_shouldThrowException() {
        // Given
        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));
        given(ticketRepository.findTicketsBySchedule_IdAndSeat_Id(1, 1)).willReturn(new ArrayList<>());
        given(seatRepository.findFirstById(1)).willReturn(null); // Ghế không tồn tại trong DB

        // When & Then
        assertThatThrownBy(() -> billService.createNewBill(validBookingRequest))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void createNewBill_withMultipleSeats_shouldCreateMultipleTickets() {
        // Given - Đặt 3 ghế cùng lúc
        validBookingRequest.setListSeatIds(Arrays.asList(1, 2, 3));

        Seat seat2 = new Seat(); seat2.setId(2); seat2.setName("A2"); seat2.setSeatType(SeatType.NORMAL);
        Seat seat3 = new Seat(); seat3.setId(3); seat3.setName("A3"); seat3.setSeatType(SeatType.VIP);

        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));
        given(ticketRepository.findTicketsBySchedule_IdAndSeat_Id(anyInt(), anyInt())).willReturn(new ArrayList<>());
        given(seatRepository.findFirstById(1)).willReturn(mockSeat);
        given(seatRepository.findFirstById(2)).willReturn(seat2);
        given(seatRepository.findFirstById(3)).willReturn(seat3);
        given(priceCalculationService.calculateSeatPrice(any(), any())).willReturn(80000.0);
        given(billRepository.save(any(Bill.class))).willReturn(savedBill);

        // When
        BillDto result = billService.createNewBill(validBookingRequest);

        // Then
        assertThat(result).isNotNull();
        verify(ticketRepository, times(3)).save(any(Ticket.class)); // Phải tạo đúng 3 vé
    }

    @Test
    void createNewBill_withFoodItems_shouldCreateBillFoodAndUpdateTotal() {
        // Given - Đặt vé kèm bắp nước
        BookingFoodItemDto foodReq = new BookingFoodItemDto();
        foodReq.setFoodId(1);
        foodReq.setQuantity(2);
        foodReq.setPrice(45000.0);
        validBookingRequest.setFoods(Arrays.asList(foodReq));

        FoodItem foodItem = new FoodItem();
        foodItem.setId(1);
        foodItem.setName("Combo Bắp Nước");
        foodItem.setPrice(45000.0);

        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));
        given(userRepository.findById(1L)).willReturn(Optional.of(mockUser));
        given(ticketRepository.findTicketsBySchedule_IdAndSeat_Id(1, 1)).willReturn(new ArrayList<>());
        given(seatRepository.findFirstById(1)).willReturn(mockSeat);
        given(priceCalculationService.calculateSeatPrice(any(), any())).willReturn(80000.0);
        given(billRepository.save(any(Bill.class))).willReturn(savedBill);
        given(foodItemRepository.findById(1)).willReturn(Optional.of(foodItem));

        // When
        BillDto result = billService.createNewBill(validBookingRequest);

        // Then
        assertThat(result).isNotNull();
        verify(billFoodRepository, times(1)).save(any(BillFood.class));
        verify(billRepository, times(2)).save(any(Bill.class)); // Lưu 2 lần: lần 1 tạo bill, lần 2 cập nhật tổng tiền
    }
}
