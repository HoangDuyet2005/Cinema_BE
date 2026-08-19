package com.example.goldenticketnew.payload.article.request;

import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ArticleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;



@Data
public class AddNewArRequest {
    private String mainImage;

    private String title;

    private String brief;

    private String description;
    @JsonIgnore
    private ArticleType type;
    @JsonIgnore
    private ArticleStatus status;


    private String keyword;

    private String shortDescription;

    private String thumbnail;

}
