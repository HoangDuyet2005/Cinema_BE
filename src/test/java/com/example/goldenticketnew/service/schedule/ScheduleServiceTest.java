package com.example.goldenticketnew.service.schedule;

import com.example.goldenticketnew.dtos.ScheduleDto;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Branch;
import com.example.goldenticketnew.model.Movie;
import com.example.goldenticketnew.model.Room;
import com.example.goldenticketnew.model.Schedule;
import com.example.goldenticketnew.repository.IScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceTest {

    @Mock
    private IScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    private Schedule mockSchedule;

    @BeforeEach
    void setUp() {
        mockSchedule = new Schedule();
        mockSchedule.setId(1);
        mockSchedule.setPrice(75000.0);
        mockSchedule.setStartDate(LocalDate.of(2026, 12, 1));
        mockSchedule.setStartTime(LocalTime.of(14, 30));
        
        Movie m = new Movie(); m.setId(1); m.setName("Avenger");
        Branch b = new Branch(); b.setId(1); b.setName("CGV");
        Room r = new Room(); r.setId(1); r.setName("R1");
        
        mockSchedule.setMovie(m);
        mockSchedule.setBranch(b);
        mockSchedule.setRoom(r);
    }

    @Test
    void getScheduleById_whenScheduleExists_shouldReturnDto() {
        // Given
        given(scheduleRepository.findById(1)).willReturn(Optional.of(mockSchedule));

        // When
        ScheduleDto result = scheduleService.getScheduleById(1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getPrice()).isEqualTo(75000.0);
        assertThat(result.getMovie().getName()).isEqualTo("Avenger");
    }

    @Test
    void getScheduleById_whenScheduleNotFound_shouldThrowInternalException() {
        // Given
        given(scheduleRepository.findById(99)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> scheduleService.getScheduleById(99))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void getStartTimes_whenValidParams_shouldReturnFormattedStringList() {
        // Given
        LocalDate queryDate = LocalDate.of(2026, 12, 1);
        List<LocalTime> times = Arrays.asList(LocalTime.of(9, 5), LocalTime.of(14, 30));
        given(scheduleRepository.getStartTimeByMovie_IdAndBranch_IdAndStartDate(1, 1, queryDate))
                .willReturn(times);

        // When
        List<String> result = scheduleService.getStartTimes(1, 1, queryDate);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo("09:05");
        assertThat(result.get(1)).isEqualTo("14:30");
    }
}
