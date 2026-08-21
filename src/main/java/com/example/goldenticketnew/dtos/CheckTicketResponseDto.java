package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTicketResponseDto {
    private String status; // VALID, ALREADY_CHECKED_IN, WAITING_PAYMENT, EXPIRED_OR_CANCELLED, NOT_FOUND
    private String message;
    private BillDetailDto billDetail;
}