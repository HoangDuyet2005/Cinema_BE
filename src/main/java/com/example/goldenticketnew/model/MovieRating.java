package com.example.goldenticketnew.model;

import com.example.goldenticketnew.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "movie_rating")
@NoArgsConstructor
@AllArgsConstructor
public class MovieRating extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Integer movieId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "rating_score", nullable = false)
    private Double ratingScore;
}