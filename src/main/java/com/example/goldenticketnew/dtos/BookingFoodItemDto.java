package com.example.goldenticketnew.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingFoodItemDto {
    private Integer foodId;
    private Integer quantity;
    private Double price;
}