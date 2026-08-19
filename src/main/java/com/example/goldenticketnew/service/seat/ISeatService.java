package com.example.goldenticketnew.service.seat;


import com.example.goldenticketnew.dtos.SeatDto;

import java.util.List;

public interface ISeatService {
    List<SeatDto> getSeatsByScheduleId(Integer scheduleId);
}
