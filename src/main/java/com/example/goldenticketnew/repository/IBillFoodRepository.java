package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.BillFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IBillFoodRepository extends JpaRepository<BillFood, Integer> {
    List<BillFood> findByBillId(Integer billId);
}
