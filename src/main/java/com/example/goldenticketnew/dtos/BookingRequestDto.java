package com.example.goldenticketnew.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class BookingRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Integer scheduleId;
    @NotEmpty
    private List<Integer> listSeatIds;
    
    private List<BookingFoodItemDto> foods;

    // Toàn bộ tham số VNPay trả về (vnp_*): bằng chứng đã thanh toán, bắt buộc khi khách tự đặt vé
    private Map<String, String> paymentProof;

    // Do controller quyết định theo quyền (khách: bắt buộc; nhân viên bán tại quầy: không) - client không set được
    @JsonIgnore
    private boolean paymentRequired;

    @JsonIgnore
    private Integer billId;
}