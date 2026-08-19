package com.example.goldenticketnew.payload.article.request;

import com.example.goldenticketnew.enums.ArticleStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeArticleStatusRequest {
    @NotNull
    private Long articleId;
    @NotNull
    private ArticleStatus status;
}
