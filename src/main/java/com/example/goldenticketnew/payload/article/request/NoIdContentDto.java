package com.example.goldenticketnew.payload.article.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoIdContentDto {
    private Integer priority;

    private String description;

    private String image;
}
