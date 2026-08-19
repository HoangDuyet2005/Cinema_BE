package com.example.goldenticketnew.payload.interaction.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddNewCommentRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long articleId;
    private String description;
}
