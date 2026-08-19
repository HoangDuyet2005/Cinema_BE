package com.example.goldenticketnew.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class BookingRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private Integer scheduleId;
    @NotEmpty
    private List<Integer> listSeatIds;
    @JsonIgnore
    private Integer billId;
}
