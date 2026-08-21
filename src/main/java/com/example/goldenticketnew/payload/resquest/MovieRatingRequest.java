package com.example.goldenticketnew.payload.resquest;

import lombok.Data;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class MovieRatingRequest {
    @NotNull
    private Integer movieId;
    @NotNull
    private Long userId;
    @NotNull
    @Min(1)
    @Max(10)
    private Double score;
}