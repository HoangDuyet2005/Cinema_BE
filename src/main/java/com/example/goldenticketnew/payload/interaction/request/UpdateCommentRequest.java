package com.example.goldenticketnew.payload.interaction.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCommentRequest {
    @NotNull
    private Long id;
    private String description;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}