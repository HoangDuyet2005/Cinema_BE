package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.FoodItemDto;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.service.food.IFoodItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/api/concessions", produces = "application/json")
@Tag(name = "Concession / Food Controller", description = "Thao tác với danh mục bắp nước combo")
public class FoodItemController {

    @Autowired
    private IFoodItemService foodItemService;

    @Operation(summary = "Lấy toàn bộ danh sách bắp nước và combo đang kinh doanh")
    @GetMapping
    public ResponseEntity<ResponseBase<List<FoodItemDto>>> getAllConcessions() {
        return ResponseEntity.ok(new ResponseBase<>(foodItemService.getAllActiveFoodItems()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<FoodItemDto>> getConcessionById(@PathVariable Integer id) {
        return ResponseEntity.ok(new ResponseBase<>(foodItemService.getFoodItemById(id)));
    }
}
