package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface IMovieRepository extends JpaRepository<Movie, Integer> , JpaSpecificationExecutor<Movie> {
    List<Movie> findMoviesByIsShowingOrderByIdDesc(Integer isShowing);
    List<Movie> findMoviesByIsShowingAndNameContaining(Integer isShowing,String name);
    Movie findMovieById(Integer id);
    Optional<Movie> findMovieByName(String name);
}