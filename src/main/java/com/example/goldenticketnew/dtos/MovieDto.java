package com.example.goldenticketnew.dtos;

import com.example.goldenticketnew.model.Movie;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class MovieDto {
    private int id;
    private String name;
    private String smallImageURl;
    private String shortDescription;
    private String longDescription;
    private String largeImageURL;
    private String director;
    private String actors;
    private String categories;
    private LocalDate releaseDate;
    private int duration;
    private String trailerURL;
    private String language;
    private String rated;

    public MovieDto(Movie movie) {
        this.id = movie.getId();
        this.name = movie.getName();
        this.smallImageURl = movie.getSmallImageURl();
        this.shortDescription = movie.getShortDescription();
        this.longDescription = movie.getLongDescription();
        this.largeImageURL = movie.getLargeImageURL();
        this.director = movie.getDirector();
        this.actors = movie.getActors();
        this.categories = movie.getCategories();
        this.releaseDate = movie.getReleaseDate();
        this.duration = movie.getDuration();
        this.trailerURL = movie.getTrailerURL();
        this.language = movie.getLanguage();
        this.rated = movie.getRated();
    }
}
