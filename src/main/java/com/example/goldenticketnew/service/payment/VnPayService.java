package com.example.goldenticketnew.service.payment;

import com.example.goldenticketnew.config.VNPayConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class VnPayService {

    @Value("${vnpay.tmnCode}")
    private String tmnCode;

    @Value("${vnpay.hashSecret}")
    private String hashSecret;

    /** Chữ ký vnp_SecureHash trên các tham số VNPay trả về có đúng là do VNPay ký bằng secret của mình không. */
    public boolean isValidSignature(Map<String, String> params) {
        if (params == null) return false;
        Map<String, String> fields = new HashMap<>(params);
        String receivedHash = fields.remove("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");

        List<String> names = new ArrayList<>(fields.keySet());
        Collections.sort(names);
        StringBuilder hashData = new StringBuilder();
        for (String name : names) {
            String value = fields.get(name);
            if (value == null || value.isEmpty()) continue;
            if (hashData.length() > 0) hashData.append('&');
            hashData.append(name).append('=').append(URLEncoder.encode(value, StandardCharsets.US_ASCII));
        }
        return receivedHash != null && VNPayConfig.hmacSHA512(hashSecret, hashData.toString()).equalsIgnoreCase(receivedHash);
    }

    /** VNPay xác nhận thanh toán THÀNH CÔNG, đúng chữ ký và đúng merchant của hệ thống này. */
    public boolean isPaidSuccessfully(Map<String, String> params) {
        return isValidSignature(params)
                && tmnCode.equals(params.get("vnp_TmnCode"))
                && "00".equals(params.get("vnp_ResponseCode"))
                && (params.get("vnp_TransactionStatus") == null || "00".equals(params.get("vnp_TransactionStatus")));
    }
}
