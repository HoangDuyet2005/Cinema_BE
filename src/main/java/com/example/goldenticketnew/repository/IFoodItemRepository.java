package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFoodItemRepository extends JpaRepository<FoodItem, Integer> {
    List<FoodItem> findAllByStatus(Integer status);
}
