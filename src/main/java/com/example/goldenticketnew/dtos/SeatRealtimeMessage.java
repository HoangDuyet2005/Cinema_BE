package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRealtimeMessage {
    private String type;        // "SELECT", "UNSELECT", "HOLD", "RELEASE", "BOOKED", "SYNC"
    private Integer scheduleId; // ID lịch chiếu
    private Integer seatId;     // ID ghế
    private String seatName;    // Tên ghế (A1, C3,...)
    private Integer userId;     // ID user thực hiện
    private Integer isOccupied; // 0: Trống, 1: Đã bán, 2: Đang giữ chỗ
    private List<Integer> holdingSeatIds; // Danh sách ID các ghế đang được giữ
}