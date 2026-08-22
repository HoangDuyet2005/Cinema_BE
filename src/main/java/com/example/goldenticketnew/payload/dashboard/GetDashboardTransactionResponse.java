package com.example.goldenticketnew.payload.dashboard;

import com.example.goldenticketnew.dtos.DayTransactionReport;
import com.example.goldenticketnew.dtos.TopMovieReportDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetDashboardTransactionResponse {
    private  Long totalTicket;
    private Long totalIncome ;
    private Integer totalTransaction;
    private List<DayTransactionReport> dayTransactionReports;
    private Double averageOccupancyRate;
    private List<TopMovieReportDto> topMovies;
}
