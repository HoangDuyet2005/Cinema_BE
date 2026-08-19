package com.example.goldenticketnew.model;

import com.example.goldenticketnew.model.audit.UserDateAudit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Table(name = "movie")
@Entity
@NoArgsConstructor
@FieldNameConstants
@Builder
@AllArgsConstructor
public class Movie extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(length = 1000)
    private String smallImageURl;
    @Column(length = 500)
    private String shortDescription;
    @Column(length = 1000)
    private String longDescription;
    @Column(length = 1000)
    private String largeImageURL;
    private String director;
    private String actors;
    private String categories;
    private LocalDate releaseDate;
    private int duration;
    @Column(length = 1000)
    private String trailerURL;
    private String language;
    private String rated;
    private int isShowing;
    public static Movie addNewMovie(
        Integer id,
        String name,
        String smallImageURl,
        String shortDescription,
        String longDescription,
        String largeImageURL,
        String director,
        String actors,
        String categories,
        LocalDate releaseDate,
        int duration,
        String trailerURL,
        String language,
        String rated,
        int isShowing) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setSmallImageURl(smallImageURl);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setLargeImageURL(largeImageURL);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setCategories(categories);
        movie.setReleaseDate(releaseDate);
        movie.setDuration(duration);
        movie.setTrailerURL(trailerURL);
        movie.setLanguage(language);
        movie.setRated(rated);
        movie.setIsShowing(isShowing);
        return movie;
    }
}
