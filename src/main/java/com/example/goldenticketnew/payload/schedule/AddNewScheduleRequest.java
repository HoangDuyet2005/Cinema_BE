package com.example.goldenticketnew.payload.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddNewScheduleRequest {
    @NotNull
    private Integer movieId;
    @NotNull
    private Integer branchId;
    @NotNull
    private Integer roomId;
    @NotBlank
    private String startDate;
    @NotBlank
    private String startTime;
    @Positive
    private Double price;
}
