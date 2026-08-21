package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String category;
    private Integer status;
}
