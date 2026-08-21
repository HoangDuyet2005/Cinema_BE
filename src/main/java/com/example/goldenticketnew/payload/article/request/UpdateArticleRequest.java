package com.example.goldenticketnew.payload.article.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateArticleRequest {
    @NotNull
    private Long id;

    private String mainImage;

    private String title;

    private String brief;

    private String description;

    private String shortDescription;

    private String thumbnail;

    private Long categoryId;

    private String keyword;

}
