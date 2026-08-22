package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByNameIgnoreCase(String name);
}
