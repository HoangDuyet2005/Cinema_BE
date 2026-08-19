package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionReportSuccess {
    private String date;
    private Long userId;
    private Long amountTransaction;
    private Double precipitation;
}
