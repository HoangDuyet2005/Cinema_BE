package com.example.goldenticketnew.controller;


import com.example.goldenticketnew.dtos.SeatDto;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.service.seat.ISeatService;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/seats")
@Tag(name = "Seat Controller", description = "Thao tác với Seat")
public class SeatController {
    @Autowired
    private ISeatService seatService;

    @GetMapping
    public ResponseEntity<ResponseBase<List<SeatDto>>> getSeatsByScheduleId(@RequestParam Integer scheduleId){
        return ResponseEntity.ok(new ResponseBase<>(seatService.getSeatsByScheduleId(scheduleId)));
    }

}
