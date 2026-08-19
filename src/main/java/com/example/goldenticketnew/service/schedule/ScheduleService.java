package com.example.goldenticketnew.service.schedule;

import com.example.goldenticketnew.dtos.ScheduleDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Branch;
import com.example.goldenticketnew.model.Movie;
import com.example.goldenticketnew.model.Room;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleChatBoxRequest;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleRequest;
import com.example.goldenticketnew.payload.schedule.AddNewScheduleRequest;
import com.example.goldenticketnew.repository.IBranchRepository;
import com.example.goldenticketnew.repository.IMovieRepository;
import com.example.goldenticketnew.repository.IRoomRepository;
import com.example.goldenticketnew.repository.IScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService implements IScheduleService {

    private final IScheduleRepository scheduleRepository;

    private final IBranchRepository branchRepository;

    private final IMovieRepository movieRepository;

    private final IRoomRepository roomRepository;


    private final ModelMapper modelMapper;

    @Override
    public List<String> getStartTimes(Integer movieId, Integer branchId, LocalDate startDate) {
        return scheduleRepository.getStartTimeByMovie_IdAndBranch_IdAndStartDate(movieId, branchId, startDate)
            .stream().map(localTime -> localTime.format(DateTimeFormatter.ofPattern("HH:mm")))
            .collect(Collectors.toList());
    }

    @Override
    public List<ScheduleDto> getSchedules(GetAllScheduleRequest request) {
        return scheduleRepository.findAll(request.getSpecification())
            .stream().map(schedule -> new ScheduleDto(schedule))
            .collect(Collectors.toList());
    }
    @Override
    public List<ScheduleDto> getSchedulesChatBox(GetAllScheduleChatBoxRequest request) {
        return scheduleRepository.findAll(request.getSpecification())
            .stream().map(schedule -> new ScheduleDto(schedule))
            .collect(Collectors.toList());
    }

    @Override
    public PageResponse<ScheduleDto> getAllSchedule(GetAllScheduleRequest request) {
        Page<Schedule> schedulePage = scheduleRepository.findAll(request.getSpecification(), request.getPageable());
        return new PageResponse<>(schedulePage.map(ScheduleDto::new));
    }

    @Override
    public ScheduleDto addNewSchedule(AddNewScheduleRequest request) {
        Schedule schedule = new Schedule();
        Branch branch = branchRepository.findById(request.getBranchId()).orElseThrow(() -> new InternalException(ResponseCode.BRANCH_NOT_FOUND));
        Movie movie = movieRepository.findById(request.getMovieId()).orElseThrow(() -> new InternalException(ResponseCode.MOVIE_NOT_FOUND));
        Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new InternalException(ResponseCode.MOVIE_NOT_FOUND));
        schedule.setBranch(branch);
        schedule.setMovie(movie);
        schedule.setRoom(room);
        schedule.setPrice(request.getPrice());
        schedule.setStartDate(LocalDate.parse(request.getStartDate()));
        schedule.setStartTime(LocalTime.parse(request.getStartTime()));
        schedule = scheduleRepository.save(schedule);
        return new ScheduleDto(schedule);
    }
}
