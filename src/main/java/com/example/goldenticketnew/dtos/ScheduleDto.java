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
    private String format;
    private BranchDto branch;
    private RoomDto room;
    private MovieDto movie;
    private Double price;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.startDate = schedule.getStartDate();
        this.startTime = schedule.getStartTime();
        this.format = schedule.getFormat() != null ? schedule.getFormat() : (schedule.getRoom() != null ? schedule.getRoom().getFormat() : "2D");
        if (schedule.getBranch() != null) {
            this.branch = new BranchDto(schedule.getBranch());
        }
        if (schedule.getRoom() != null) {
            this.room = new RoomDto(schedule.getRoom());
        }
        if (schedule.getMovie() != null) {
            this.movie = new MovieDto(schedule.getMovie());
        }
        this.price = schedule.getPrice();
    }
}
