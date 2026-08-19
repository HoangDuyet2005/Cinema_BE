package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ISeatRepository extends JpaRepository<Seat, Integer> {
    List<Seat> getSeatByRoom_Id(Integer roomId);

    Seat findFirstById(Integer id);

    List<Seat> findAllByNameContaining(String name);
}