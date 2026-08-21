package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.Bill;
import com.example.goldenticketnew.model.BillFood;
import com.example.goldenticketnew.model.Ticket;
import com.example.goldenticketnew.payload.UserProfile;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class BillDetailDto {
    private int id;
    private LocalDateTime createdTime;
    private UserProfile user;
    private BillStatus status;
    private Double price;
    private Integer amountTicket;
    private String bookingCode;
    private Boolean isCheckedIn;
    private LocalDateTime checkInTime;
    private String qrCode;
    private ScheduleDto schedule;
    private List<SeatDto> seats = new ArrayList<>();
    private List<BillFoodDto> foods = new ArrayList<>();

    public BillDetailDto(Bill bill, List<Ticket> tickets) {
        this(bill, tickets, null);
    }

    public BillDetailDto(Bill bill, List<Ticket> tickets, List<BillFood> billFoods) {
        this.id = bill.getId();
        this.createdTime = bill.getCreatedTime();
        if (bill.getUser() != null) {
            this.user = new UserProfile(bill.getUser());
        }
        this.status = bill.getStatus();
        this.price = bill.getPrice();
        this.amountTicket = tickets != null ? tickets.size() : 0;
        this.bookingCode = bill.getBookingCode();
        this.isCheckedIn = bill.getIsCheckedIn() != null ? bill.getIsCheckedIn() : false;
        this.checkInTime = bill.getCheckInTime();
        this.qrCode = bill.getQrCode();
        if (tickets != null && !tickets.isEmpty()) {
            for (Ticket t : tickets) {
                try {
                    if (t.getSchedule() != null && this.schedule == null) {
                        this.schedule = new ScheduleDto(t.getSchedule());
                    }
                    if (t.getSeat() != null) {
                        this.seats.add(new SeatDto(t.getSeat()));
                    }
                } catch (Exception ignored) {
                }
            }
        }
        if (billFoods != null && !billFoods.isEmpty()) {
            this.foods = billFoods.stream().map(BillFoodDto::new).collect(Collectors.toList());
        }
    }
}