package com.example.goldenticketnew.payload.seat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigureRoomSeatsRequest {
    private Integer roomId;
    private List<SeatConfigItem> seats;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SeatConfigItem {
        private String name;
        private Integer seatType; // 0: NORMAL, 1: VIP, 2: COUPLE, 3: TRIPLE
    }
}
