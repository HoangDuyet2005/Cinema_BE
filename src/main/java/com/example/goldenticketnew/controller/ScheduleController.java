package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.ArticleDto;
import com.example.goldenticketnew.dtos.ScheduleDto;
import com.example.goldenticketnew.enums.ArticleType;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.payload.article.request.AddNewArticleRequest;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleChatBoxRequest;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleRequest;
import com.example.goldenticketnew.payload.schedule.AddNewScheduleRequest;
import com.example.goldenticketnew.service.schedule.IScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/schedule", produces = "application/json")
@Tag(name = "Schedule Controller", description = "Thao tác với Schedule")
public class ScheduleController {
    @Autowired
    private IScheduleService scheduleService;
    @Operation(
        summary = "Thêm mới lịch chiếu ",
        description = "- Thêm mới lịch chiếu phim"
    )
    @PostMapping("/add")
    public ResponseEntity<ResponseBase<ScheduleDto>> addNewSchedule(@Valid @ParameterObject AddNewScheduleRequest request) {
       List<String> listStartTime = scheduleService.getStartTimes(request.getMovieId(), request.getBranchId(), LocalDate.parse(request.getStartDate()));
       for(String timeElement : listStartTime){
           if(timeElement.equals(request.getStartTime()))
               throw new InternalException(ResponseCode.COMMON_ERROR);
       }
        return ResponseEntity.ok(new ResponseBase<>(scheduleService.addNewSchedule(request)));
    }

    @GetMapping("/start-times")
    public List<String> getStartTimes(@RequestParam Integer movieId, @RequestParam Integer branchId,
                                         @RequestParam String startDate) {
        return scheduleService.getStartTimes(movieId,branchId,LocalDate.parse(startDate));
    }
    @Operation(
        summary = "Get All Schedule với filter (List)",
        description = "- Get All Schedule với filter"
    )
    @GetMapping
    public ResponseEntity<ResponseBase<List<ScheduleDto>>> getSchedules(@ParameterObject GetAllScheduleRequest request){
        return new ResponseEntity<>(new ResponseBase<>(scheduleService.getSchedules(request)), HttpStatus.OK);
    }

    @Operation(
        summary = "Get All Schedule với filter (List) Chat Box",
        description = "- Get All Schedule với filter Chat Box"
    )
    @GetMapping("/chatbox")
    public ResponseEntity<ResponseBase<List<ScheduleDto>>> getSchedulesChatBox(@ParameterObject GetAllScheduleChatBoxRequest request){
        return new ResponseEntity<>(new ResponseBase<>(scheduleService.getSchedulesChatBox(request)), HttpStatus.OK);
    }
    @Operation(
        summary = "Get All Schedule với filter và paging ",
        description = "- Get All Schedule với filter và paging"
    )
    @GetMapping("/getAll")
    public ResponseEntity<ResponseBase<PageResponse<ScheduleDto>>> getSchedules(@ParameterObject Pageable pageable, @ParameterObject GetAllScheduleRequest request ){
        request.setPageable(pageable);
        return new ResponseEntity<>(new ResponseBase<>(scheduleService.getAllSchedule(request)), HttpStatus.OK);
    }

}
