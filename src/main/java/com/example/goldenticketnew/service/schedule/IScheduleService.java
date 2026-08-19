package com.example.goldenticketnew.service.schedule;


import com.example.goldenticketnew.dtos.ScheduleDto;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleChatBoxRequest;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleRequest;
import com.example.goldenticketnew.payload.schedule.AddNewScheduleRequest;

import java.time.LocalDate;
import java.util.List;

public interface IScheduleService {
    List<String> getStartTimes(Integer movieId, Integer branchId, LocalDate startDate);
    List<ScheduleDto> getSchedules(GetAllScheduleRequest request);
    List<ScheduleDto> getSchedulesChatBox(GetAllScheduleChatBoxRequest request);
    PageResponse<ScheduleDto> getAllSchedule(GetAllScheduleRequest request);

    ScheduleDto addNewSchedule(AddNewScheduleRequest request);
}
