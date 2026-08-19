package com.example.goldenticketnew.payload.resquest;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class UpdateMovieRequest {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    private String smallImageURl;

    private String shortDescription;

    private String longDescription;

    private String largeImageURL;
    @NotBlank
    private String director;
    @NotBlank
    private String actors;
    @NotBlank
    private String categories;
    @NotNull
    private LocalDate releaseDate;

    @NotNull
    private Integer duration;

    private String trailerURL;

    private String language;
    @NotBlank
    private String rated;

    @NotNull
    private int isShowing;
}
