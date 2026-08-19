package com.example.goldenticketnew.payload.interaction.request;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CheckUserLikeRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long articleId;
}