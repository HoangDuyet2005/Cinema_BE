package com.example.goldenticketnew.service.bill;


import com.example.goldenticketnew.dtos.*;

import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionRequest;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionResponse;

import java.util.List;

public interface IBillService {
    BillDto createNewBill(BookingRequestDto bookingRequestDTO);

    void removeBill(DeleteBillTicketRequest request);

    BillDto payBill(Integer id);

    BillDto bookingHandler(BookingRequestDto bookingRequestDTO);

    GetDashboardTransactionResponse getDashBoardTransaction(GetDashboardTransactionRequest request);

    List<TransactionReportSuccess> getTranS(String dateTime);

    List<UserReportDto> getUserDashBoard(BillStatus status);

    List<BillDto> getList(GetDashboardTransactionRequest request);

}
