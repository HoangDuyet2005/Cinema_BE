package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.model.Bill;
import com.example.goldenticketnew.payload.UserProfile;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BillDto {
    private int id;
    private LocalDateTime createdTime;
    private UserProfile user;

    private BillStatus status;

    private Double price;

    public BillDto(Bill bill) {
        this.id = bill.getId();
        this.createdTime = bill.getCreatedTime();
        this.user = new UserProfile(bill.getUser());
        this.status = bill.getStatus();
        this.price = bill.getPrice();
    }
}
