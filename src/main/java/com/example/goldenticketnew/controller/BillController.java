package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.*;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionRequest;
import com.example.goldenticketnew.payload.dashboard.GetDashboardTransactionResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.security.CurrentUser;
import com.example.goldenticketnew.security.UserPrincipal;
import com.example.goldenticketnew.service.bill.IBillService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<BillDetailDto> confirmCheckIn(@RequestParam Integer billId) {
        return ResponseEntity.ok(billService.confirmCheckIn(billId));
    }

    @Operation(
        summary = "Tạo hóa đơn ",
        description = "- Tạo hóa đơn (chỉ tự đặt cho chính mình, trừ khi là ADMIN/STAFF đặt hộ tại quầy)"
    )
    @PostMapping("/create-new-bill")
    public ResponseEntity<ResponseBase<BillDto>> createNewBill(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody BookingRequestDto bookingRequestDTO) {
        if (!isStaffOrAdmin(currentUser) && !currentUser.getId().equals(bookingRequestDTO.getUserId())) {
            // Trước đây userId trong request được tin tưởng tuyệt đối, cho phép đặt vé dưới tên
            // người khác (IDOR) - đã vá: khách thường chỉ được đặt vé cho chính mình.
            throw new AccessDeniedException("Không có quyền đặt vé cho user khác");
        }
        return new ResponseEntity<>(new ResponseBase<>(billService.bookingHandler(bookingRequestDTO), 899, "Dat ve thanh cong"), HttpStatus.OK);
    }

    @Operation(
        summary = "Huy hóa đơn ",
        description = "- Huỷ hóa đơn (ADMIN/STAFF hoặc chính chủ hóa đơn)"
    )
    @PostMapping("/delete")
    public ResponseEntity<String> xoaNewBill(@CurrentUser UserPrincipal currentUser, @Valid @ParameterObject DeleteBillTicketRequest request) {
        try {
            requireOwnerOrStaff(currentUser, request.getBillId());
            billService.removeBill(request);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>("Bạn đã xoa ve thành công !", HttpStatus.OK);
    }

    @Operation(
        summary = "Thanh toan hóa đơn ",
        description = "- Thanh toan hóa đơn (ADMIN/STAFF hoặc chính chủ hóa đơn)"
    )
    @PostMapping("/payment")
    public ResponseEntity<BillDto> payBill(@CurrentUser UserPrincipal currentUser, @Valid @Parameter Integer id) {
        requireOwnerOrStaff(currentUser, id);
        return new ResponseEntity<>(billService.payBill(id), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy DashBoard Bill ",
        description = "- Lấy DashBoard Bill (ADMIN/STAFF)"
    )
    @GetMapping("/getBillDashBoard")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<GetDashboardTransactionResponse> getBillDashBoard(@Valid @ParameterObject GetDashboardTransactionRequest request) {
        return new ResponseEntity<>(billService.getDashBoardTransaction(request), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy thống kê giao dịch thành công ",
        description = "- Lấy thống kê giao dịch thành công (ADMIN/STAFF)"
    )
    @GetMapping("/getBillSuccess")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<TransactionReportSuccess>> getBillDashBoard(@Valid @Parameter String dateTime) {
        return new ResponseEntity<>(billService.getTranS(dateTime), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy DashBoard User ",
        description = "- Lấy DashBoard User (ADMIN/STAFF)"
    )
    @GetMapping("/getUserDashBoard")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<UserReportDto>> getUserDashBoard(@Valid @ParameterObject GetDashboardTransactionRequest request) {
        return new ResponseEntity<>(billService.getUserDashBoard(request), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy Danh sach Bill ",
        description = "- Lấy Danh sach Bill (ADMIN/STAFF)"
    )
    @GetMapping("/getAllBill")
    @PreAuthorize("hasAnyRole('ADMIN','STAFF')")
    public ResponseEntity<List<BillDto>> getList(@Valid @ParameterObject GetDashboardTransactionRequest request) {
        return new ResponseEntity<>(billService.getList(request), HttpStatus.OK);
    }

    @Operation(
        summary = "Lấy chi tiết hóa đơn",
        description = "- Lấy chi tiết hóa đơn kèm vé và ghế (ADMIN/STAFF hoặc chính chủ hóa đơn)"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BillDetailDto> getBillDetail(@CurrentUser UserPrincipal currentUser, @PathVariable Integer id) {
        BillDetailDto detail = billService.getBillDetail(id);
        if (!isStaffOrAdmin(currentUser) && (detail.getUser() == null || !detail.getUser().getId().equals(currentUser.getId()))) {
            // Trước đây bất kỳ user nào đăng nhập cũng xem được chi tiết hóa đơn của người khác
            // chỉ cần biết id (IDOR) - đã vá.
            throw new AccessDeniedException("Không có quyền xem hóa đơn này");
        }
        return new ResponseEntity<>(detail, HttpStatus.OK);
    }

    private boolean isStaffOrAdmin(UserPrincipal currentUser) {
        return currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_STAFF"));
    }

    private void requireOwnerOrStaff(UserPrincipal currentUser, Integer billId) {
        if (isStaffOrAdmin(currentUser)) return;
        BillDetailDto detail = billService.getBillDetail(billId);
        if (detail.getUser() == null || !detail.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Không có quyền thao tác trên hóa đơn này");
        }
    }
}
