package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.ScheduleDto;
import com.example.goldenticketnew.model.Branch;
import com.example.goldenticketnew.model.Movie;
import com.example.goldenticketnew.model.Room;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.payload.resquest.GetAllScheduleRequest;
import com.example.goldenticketnew.service.schedule.IScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.goldenticketnew.security.CustomUserDetailsService;
import com.example.goldenticketnew.security.JwtAuthenticationEntryPoint;
import com.example.goldenticketnew.security.JwtTokenProvider;

@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ScheduleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IScheduleService scheduleService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;

    private ScheduleDto mockScheduleDto;

    @BeforeEach
    void setUp() {
        Schedule schedule = new Schedule();
        schedule.setId(1);
        schedule.setPrice(75000.0);
        schedule.setStartDate(LocalDate.of(2026, 12, 1));
        schedule.setStartTime(LocalTime.of(14, 30));

        Movie m = new Movie(); m.setId(1); m.setName("Avenger");
        Branch b = new Branch(); b.setId(1); b.setName("CGV");
        Room r = new Room(); r.setId(1); r.setName("R1");

        schedule.setMovie(m);
        schedule.setBranch(b);
        schedule.setRoom(r);

        mockScheduleDto = new ScheduleDto(schedule);
    }

    @Test
    void getScheduleById_whenExists_shouldReturn200AndDto() throws Exception {
        // Given
        given(scheduleService.getScheduleById(1)).willReturn(mockScheduleDto);

        // When & Then
        mockMvc.perform(get("/api/schedule/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.price").value(75000.0));
    }

    @Test
    void getStartTimes_shouldReturn200AndList() throws Exception {
        // Given
        List<String> times = Arrays.asList("09:05", "14:30");
        given(scheduleService.getStartTimes(eq(1), eq(1), any(LocalDate.class))).willReturn(times);

        // When & Then
        mockMvc.perform(get("/api/schedule/start-times")
                        .param("movieId", "1")
                        .param("branchId", "1")
                        .param("startDate", "2026-12-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("09:05"))
                .andExpect(jsonPath("$[1]").value("14:30"));
    }

    @Test
    void getSchedules_shouldReturn200AndList() throws Exception {
        // Given
        List<ScheduleDto> scheduleList = Arrays.asList(mockScheduleDto);
        given(scheduleService.getSchedules(any(GetAllScheduleRequest.class))).willReturn(scheduleList);

        // When & Then
        mockMvc.perform(get("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].price").value(75000.0));
    }
}
