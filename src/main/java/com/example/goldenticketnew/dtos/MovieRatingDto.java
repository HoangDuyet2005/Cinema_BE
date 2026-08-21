package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRatingDto {
    private Integer movieId;
    private Long userId;
    private Double score;
    private Double avgRating;
    private Long totalVotes;
    private Double userRating;
}