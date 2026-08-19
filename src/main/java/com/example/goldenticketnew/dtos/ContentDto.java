package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContentDto {

    private Integer priority;

    private String description;

    private String image;
}
