package com.example.goldenticketnew.payload.interaction.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class UpdateCommentRequest {
    @NotNull
    private Long id;
    private String description;
}
