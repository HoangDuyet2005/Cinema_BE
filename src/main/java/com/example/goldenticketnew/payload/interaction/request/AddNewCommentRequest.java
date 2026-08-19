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

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getArticleId() { return articleId; }
    public void setArticleId(Long articleId) { this.articleId = articleId; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}