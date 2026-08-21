package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.MovieDto;
import com.example.goldenticketnew.dtos.MovieRatingDto;
import com.example.goldenticketnew.model.MovieRating;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.payload.resquest.AddNewMovieRequest;
import com.example.goldenticketnew.payload.resquest.GetAllMovieRequest;
import com.example.goldenticketnew.payload.resquest.MovieRatingRequest;
import com.example.goldenticketnew.payload.resquest.UpdateMovieRequest;
import com.example.goldenticketnew.repository.MovieRatingRepository;
import com.example.goldenticketnew.service.movie.IMovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/api/movies", produces = "application/json")
@Tag(name = "Movie Controller", description = "Thao tác với movie")
public class MovieController {

    @Autowired
    private IMovieService movieService;

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @GetMapping("/showing")
    public ResponseBase<List<MovieDto>> findAllShowingMovies(){
        return new ResponseBase<>(movieService.findAllShowingMovies());
    }

    @GetMapping("/details/{movieId}")
    public ResponseBase<MovieDto> getMovieById(@PathVariable Integer movieId){
        return new ResponseBase<>(movieService.getById(movieId));
    }

    @GetMapping("/showing/search")
    public ResponseEntity<ResponseBase<List<MovieDto>>> findAllShowingMoviesByName(@RequestParam String name){
        return ResponseEntity.ok(new ResponseBase<>(movieService.findAllShowingMoviesByName(name)));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseBase<MovieDto>> updateMovie(@Valid @RequestBody UpdateMovieRequest request){
        return ResponseEntity.ok(new ResponseBase<>(movieService.updateMovie(request)));
    }
    @PostMapping("/addNew")
    public ResponseEntity<ResponseBase<MovieDto>> addNewMovie(@Valid @RequestBody AddNewMovieRequest request){
        return ResponseEntity.ok(new ResponseBase<>(movieService.addNewMovie(request)));
    }

    @DeleteMapping("/{movieId}")
    public ApiResponse deleteMovie(@Valid @PathVariable Integer movieId) {
        if(movieService.deleteMovieById(movieId))
            return new ApiResponse(true, "Delete Movie Successfully");
        return new ApiResponse(false, "Please check the id");
    }
    @Operation(
        summary = "Get All Movie với filter và paging ",
        description = "- Get All Movie với filter và paging"
    )
    @GetMapping("/getAll")
    public ResponseBase<PageResponse<MovieDto>> findAllMoviesPaging(@ParameterObject Pageable pageable, @ParameterObject GetAllMovieRequest request){
        request.setPageable(pageable);
        return new ResponseBase<>(movieService.getAllMovie(request));
    }
    @Operation(
        summary = "Get All Movie với filter (List) ",
        description = "- Get All Movie với filter (List) "
    )
    @GetMapping("/getList")
    public ResponseBase<List<MovieDto>> findAllMovies(@ParameterObject Pageable pageable, @ParameterObject GetAllMovieRequest request){
        request.setPageable(pageable);
        return new ResponseBase<>(movieService.findAllListMovies(request));
    }

    @GetMapping("/{movieId}/rating")
    public ResponseBase<MovieRatingDto> getMovieRating(
            @PathVariable Integer movieId,
            @RequestParam(required = false) Long userId) {
        Double avgRating = movieRatingRepository.getAverageRatingByMovieId(movieId);
        Long totalVotes = movieRatingRepository.countRatingsByMovieId(movieId);

        Double userRating = null;
        if (userId != null) {
            userRating = movieRatingRepository.findByUserIdAndMovieId(userId, movieId)
                    .map(MovieRating::getRatingScore)
                    .orElse(null);
        }

        if (avgRating == null || totalVotes == null || totalVotes == 0) {
            avgRating = 0.0;
            totalVotes = 0L;
        } else {
            avgRating = Math.round(avgRating * 10.0) / 10.0;
        }

        MovieRatingDto dto = MovieRatingDto.builder()
                .movieId(movieId)
                .userId(userId)
                .avgRating(avgRating)
                .totalVotes(totalVotes)
                .userRating(userRating)
                .build();

        return new ResponseBase<>(dto);
    }

    @PostMapping("/rating")
    public ResponseBase<MovieRatingDto> rateMovie(@Valid @RequestBody MovieRatingRequest request) {
        MovieRating rating = movieRatingRepository
                .findByUserIdAndMovieId(request.getUserId(), request.getMovieId())
                .orElseGet(() -> {
                    MovieRating newRating = new MovieRating();
                    newRating.setUserId(request.getUserId());
                    newRating.setMovieId(request.getMovieId());
                    return newRating;
                });

        rating.setRatingScore(request.getScore());
        movieRatingRepository.save(rating);

        Double avgRating = movieRatingRepository.getAverageRatingByMovieId(request.getMovieId());
        Long totalVotes = movieRatingRepository.countRatingsByMovieId(request.getMovieId());

        if (avgRating == null) {
            avgRating = request.getScore();
        } else {
            avgRating = Math.round(avgRating * 10.0) / 10.0;
        }

        MovieRatingDto dto = MovieRatingDto.builder()
                .movieId(request.getMovieId())
                .userId(request.getUserId())
                .score(request.getScore())
                .avgRating(avgRating)
                .totalVotes(totalVotes)
                .userRating(request.getScore())
                .build();

        return new ResponseBase<>(dto);
    }
}