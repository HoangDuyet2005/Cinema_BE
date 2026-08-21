package com.example.goldenticketnew.repository;

import com.example.goldenticketnew.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    Optional<MovieRating> findByUserIdAndMovieId(Long userId, Integer movieId);
    List<MovieRating> findByMovieId(Integer movieId);

    @Query("SELECT AVG(r.ratingScore) FROM MovieRating r WHERE r.movieId = :movieId")
    Double getAverageRatingByMovieId(@Param("movieId") Integer movieId);

    @Query("SELECT COUNT(r) FROM MovieRating r WHERE r.movieId = :movieId")
    Long countRatingsByMovieId(@Param("movieId") Integer movieId);
}