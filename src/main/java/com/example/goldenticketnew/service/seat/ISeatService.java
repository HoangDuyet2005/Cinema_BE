package com.example.goldenticketnew.service.seat;

import com.example.goldenticketnew.dtos.SeatDto;
import com.example.goldenticketnew.payload.seat.ConfigureRoomSeatsRequest;

import java.util.List;

public interface ISeatService {
    List<SeatDto> getSeatsByScheduleId(Integer scheduleId);
    List<SeatDto> getSeatsByRoomId(Integer roomId);
    void configureRoomSeats(ConfigureRoomSeatsRequest request);
}
