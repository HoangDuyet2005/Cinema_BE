package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.config.VNPayConfig;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Bill;
import com.example.goldenticketnew.model.FoodItem;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.model.Seat;
import com.example.goldenticketnew.repository.IBillRepository;
import com.example.goldenticketnew.repository.IFoodItemRepository;
import com.example.goldenticketnew.repository.IScheduleRepository;
import com.example.goldenticketnew.repository.ISeatRepository;
import com.example.goldenticketnew.security.CurrentUser;
import com.example.goldenticketnew.security.UserPrincipal;
import com.example.goldenticketnew.service.pricing.PriceCalculationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
@RequiredArgsConstructor
@Tag(name = "Payment Controller", description = "Thanh toán VNPay")
public class PaymentController {

    private final IBillRepository billRepository;
    private final IScheduleRepository scheduleRepository;
    private final ISeatRepository seatRepository;
    private final IFoodItemRepository foodItemRepository;
    private final PriceCalculationService priceCalculationService;

    @Value("${vnpay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${vnpay.hashSecret}")
    private String vnp_HashSecret;

    @Value("${vnpay.payUrl}")
    private String vnp_PayUrl;

    @Value("${vnpay.returnUrl}")
    private String vnp_ReturnUrl;

    @Operation(
        summary = "Tạo URL thanh toán VNPay",
        description = "- Số tiền KHÔNG nhận từ client nữa: nếu có billId thì lấy giá của hóa đơn đã tồn tại " +
            "(đã thuộc về user), nếu chưa có bill (đặt vé lần đầu) thì server tự tính lại giá từ scheduleId + " +
            "listSeatIds + foods giống hệt logic tạo hóa đơn, để tránh bị sửa amount khi gửi request."
    )
    @GetMapping("/create_payment")
    public Map<String, String> createPayment(
            @CurrentUser UserPrincipal currentUser,
            @RequestParam String bookingInfo,
            @RequestParam(required = false) Integer billId,
            @RequestParam(required = false) Integer scheduleId,
            @RequestParam(required = false) List<Integer> listSeatIds,
            @RequestParam(required = false) List<Integer> foodIds,
            @RequestParam(required = false) List<Integer> foodQuantities
    ) {
        boolean isStaffOrAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_STAFF"));

        long amount;
        if (billId != null) {
            // Thanh toán một hóa đơn đã tồn tại (vd: thanh toán lại hóa đơn đang chờ) -> lấy giá thật của bill
            Bill bill = billRepository.findById(billId).orElseThrow(() -> new InternalException(ResponseCode.BILL_NOT_FOUND));
            if (!isStaffOrAdmin && (bill.getUser() == null || bill.getUser().getId() != currentUser.getId())) {
                throw new AccessDeniedException("Không có quyền thanh toán hóa đơn này");
            }
            amount = Math.round(bill.getPrice() != null ? bill.getPrice() : 0);
        } else {
            // Đặt vé lần đầu: chưa có bill, server tự tính lại tổng tiền từ dữ liệu thật trong DB,
            // TUYỆT ĐỐI không dùng amount do client gửi (trước đây là lỗ hổng tamper giá).
            if (scheduleId == null || listSeatIds == null || listSeatIds.isEmpty()) {
                throw new InternalException(ResponseCode.COMMON_ERROR);
            }
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new InternalException(ResponseCode.SCHEDULE_NOT_FOUND));
            double total = 0;
            for (Integer seatId : listSeatIds) {
                Seat seat = seatRepository.findFirstById(seatId);
                if (seat == null) {
                    throw new InternalException(ResponseCode.SEAT_NOT_FOUND);
                }
                total += priceCalculationService.calculateSeatPrice(schedule, seat);
            }
            if (foodIds != null) {
                for (int i = 0; i < foodIds.size(); i++) {
                    FoodItem foodItem = foodItemRepository.findById(foodIds.get(i)).orElse(null);
                    int qty = (foodQuantities != null && i < foodQuantities.size()) ? foodQuantities.get(i) : 0;
                    if (foodItem != null && qty > 0) {
                        total += (foodItem.getPrice() != null ? foodItem.getPrice() : 0) * qty;
                    }
                }
            }
            amount = Math.round(total);
        }

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = this.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount * 100)); // amount in VND * 100
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", bookingInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnp_PayUrl + "?" + queryUrl;

        Map<String, String> response = new HashMap<>();
        response.put("url", paymentUrl);
        return response;
    }

    @Operation(
        summary = "Xác minh kết quả trả về từ VNPay",
        description = "- Kiểm tra lại chữ ký (vnp_SecureHash) của các tham số VNPay redirect về, để chống trường hợp " +
            "người dùng tự gõ thẳng URL .../payment-result?vnp_ResponseCode=00 mà không hề thanh toán thật. " +
            "FE BẮT BUỘC gọi endpoint này và chỉ được tạo hóa đơn khi valid=true."
    )
    @GetMapping("/verify-return")
    public Map<String, Object> verifyReturn(@RequestParam Map<String, String> allParams) {
        Map<String, String> fields = new HashMap<>(allParams);
        String receivedHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (itr.hasNext()) hashData.append('&');
            }
        }
        String computedHash = VNPayConfig.hmacSHA512(vnp_HashSecret, hashData.toString());
        boolean valid = receivedHash != null && computedHash.equalsIgnoreCase(receivedHash);

        Map<String, Object> result = new HashMap<>();
        result.put("valid", valid);
        result.put("responseCode", fields.get("vnp_ResponseCode"));
        return result;
    }
}
