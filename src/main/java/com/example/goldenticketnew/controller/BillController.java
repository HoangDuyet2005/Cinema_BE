package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.*;
import com.example.goldenticketnew.enums.BillStatus;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionRequest;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.service.bill.IBillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
@Tag(name = "Bill Controller", description = "Thao tác với hóa đơn")
public class BillController {
    private final IBillService billService;

    @Operation(
        summary = "Kiểm tra tính hợp lệ của mã đặt vé / QR Code",
        description = "- Quét mã QR hoặc nhập mã đặt vé để kiểm tra tính hợp lệ trước khi in vé"
    )
    @GetMapping("/check-ticket")
    public ResponseEntity<CheckTicketResponseDto> checkTicket(@RequestParam String code) {
        return ResponseEntity.ok(billService.checkTicket(code));
    }

    @Operation(
        summary = "Xác nhận nhận vé và in vé xem phim",
        description = "- Đánh dấu vé đã nhận / đã in tại quầy"
    )
    @PostMapping("/check-in")
    public ResponseEntity<BillDetailDto> confirmCheckIn(@RequestParam Integer billId) {
        return ResponseEntity.ok(billService.confirmCheckIn(billId));
    }

    @Operation(
        summary = "Tạo hóa đơn ",
        description = "- Tạo hóa đơn"
    )
    @PostMapping("/create-new-bill")
    public ResponseEntity<ResponseBase<BillDto>> createNewBill(@Valid @RequestBody BookingRequestDto bookingRequestDTO) {
        return new ResponseEntity<>(new ResponseBase<>(billService.bookingHandler(bookingRequestDTO), 899, "Dat ve thanh cong"), HttpStatus.OK);
    }

    @Operation(
        summary = "Huy hóa đơn ",
        description = "- Tạo hóa đơn"
    )
    @PostMapping("/delete")
    public ResponseEntity<String> xoaNewBill(@Valid @ParameterObject DeleteBillTicketRequest request ) {
        try {
            billService.removeBill(request);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("Bạn đã xoa ve thành công !", HttpStatus.OK);
    }

    @Operation(
        summary = "Thanh toan hóa đơn ",
        description = "- Thanh toan hóa đơn"
    )
    @PostMapping("/payment")
    public ResponseEntity<BillDto> payBill(@Valid @Parameter Integer id) {
        return new ResponseEntity<>(billService.payBill(id), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy DashBoard Bill ",
        description = "- Lấy DashBoard Bill"
    )
    @GetMapping("/getBillDashBoard")
    public ResponseEntity<GetDashboardTransactionResponse> getBillDashBoard(@Valid @ParameterObject GetDashboardTransactionRequest request) {
        return new ResponseEntity<>(billService.getDashBoardTransaction(request), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy thống kê giao dịch thành công ",
        description = "- Lấy thống kê giao dịch thành công"
    )
    @GetMapping("/getBillSuccess")
    public ResponseEntity<List<TransactionReportSuccess>> getBillDashBoard(@Valid @Parameter String dateTime) {
        return new ResponseEntity<>(billService.getTranS(dateTime), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy DashBoard User ",
        description = "- Lấy DashBoard User"
    )
    @GetMapping("/getUserDashBoard")
    public ResponseEntity<List<UserReportDto>> getUserDashBoard(@Valid @Parameter BillStatus status) {
        return new ResponseEntity<>(billService.getUserDashBoard(status), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy Danh sach Bill ",
        description = "- Lấy Danh sach Bill"
    )
    @GetMapping("/getAllBill")
    public ResponseEntity<List<BillDto>> getList(@Valid @ParameterObject GetDashboardTransactionRequest request) {
        return new ResponseEntity<>(billService.getList(request), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy chi tiết hóa đơn",
        description = "- Lấy chi tiết hóa đơn kèm vé và ghế"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDto> getBillDetail(@PathVariable Integer id) {
        return new ResponseEntity<>(billService.getBillDetail(id), HttpStatus.OK);
    }
}