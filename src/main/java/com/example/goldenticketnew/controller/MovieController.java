package com.example.goldenticketnew.controller;


import com.example.goldenticketnew.dtos.MovieDto;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.payload.resquest.AddNewMovieRequest;
import com.example.goldenticketnew.payload.resquest.GetAllMovieRequest;
import com.example.goldenticketnew.payload.resquest.UpdateMovieRequest;
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
}
