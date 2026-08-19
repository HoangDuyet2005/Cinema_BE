package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Schedule;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ScheduleDto {
    private int id;
    private LocalDate startDate;
    private LocalTime startTime;
    private BranchDto branch;
    private RoomDto room;
    private MovieDto movie;
    private Double price;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.startDate = schedule.getStartDate();
        this.startTime = schedule.getStartTime();
        this.branch = new BranchDto(schedule.getBranch());
        this.room = new RoomDto(schedule.getRoom());
        this.movie = new MovieDto(schedule.getMovie());
        this.price = schedule.getPrice();
    }
}
