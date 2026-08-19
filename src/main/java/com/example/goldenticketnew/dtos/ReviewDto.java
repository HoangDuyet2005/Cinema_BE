package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.enums.ArticleStatus;
import com.example.goldenticketnew.enums.ArticleType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private String title;
    private String brief;
    private String description;
    private ArticleStatus status;
    private ArticleType type;
}
