package com.example.goldenticketnew.payload.article.request;

import com.example.goldenticketnew.enums.ArticleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddNewArticleRequest {
    private String mainImage;
    @NotNull
    private String title;
    @NotNull
    private String brief;
    @JsonIgnore
    private ArticleType type;
    @NotNull
    private String description;

    private String image1;

    private String keyword;


}
