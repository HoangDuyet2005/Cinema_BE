package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleReportDto {
    private Long totalArticleInMonth;
    private Long totalArticleInPreviousMonth;
    private Double articleIsPrevious;

    private Long totalNewUserInMonth;
    private Long totalNewUserInPreviousMonth;
    private Double userIsPrevious;
}
