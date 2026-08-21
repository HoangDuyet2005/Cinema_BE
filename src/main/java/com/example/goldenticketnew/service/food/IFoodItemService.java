package com.example.goldenticketnew.service.food;

import com.example.goldenticketnew.dtos.FoodItemDto;
import java.util.List;

public interface IFoodItemService {
    List<FoodItemDto> getAllActiveFoodItems();
    FoodItemDto getFoodItemById(Integer id);
}
