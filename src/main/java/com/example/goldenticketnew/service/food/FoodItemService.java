package com.example.goldenticketnew.service.food;

import com.example.goldenticketnew.dtos.FoodItemDto;
import com.example.goldenticketnew.model.FoodItem;
import com.example.goldenticketnew.repository.IFoodItemRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodItemService implements IFoodItemService {

    @Autowired
    private IFoodItemRepository foodItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<FoodItemDto> getAllActiveFoodItems() {
        List<FoodItem> items = foodItemRepository.findAllByStatus(1);
        return items.stream()
                .map(item -> modelMapper.map(item, FoodItemDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FoodItemDto getFoodItemById(Integer id) {
        FoodItem item = foodItemRepository.findById(id).orElse(null);
        return item != null ? modelMapper.map(item, FoodItemDto.class) : null;
    }
}
