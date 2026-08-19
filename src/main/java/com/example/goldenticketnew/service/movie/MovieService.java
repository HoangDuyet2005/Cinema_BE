package com.example.goldenticketnew.service.movie;


import com.example.goldenticketnew.dtos.BillDto;
import com.example.goldenticketnew.dtos.MovieDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Movie;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.AddNewMovieRequest;
import com.example.goldenticketnew.payload.resquest.GetAllMovieRequest;
import com.example.goldenticketnew.payload.resquest.UpdateMovieRequest;
import com.example.goldenticketnew.repository.IMovieRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService implements IMovieService {

    @Autowired
    private IMovieRepository movieRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public MovieDto addNewMovie(AddNewMovieRequest request) {
        if(request.getReleaseDate().isBefore(LocalDate.now())){
            throw new InternalException(ResponseCode.MOVIE_RELEASE_DATE_INVALID);
        }
        Movie movie = Movie.builder()
            .duration(request.getDuration())
            .shortDescription(request.getShortDescription())
            .actors(request.getActors())
            .categories(request.getCategories())
            .director(request.getDirector())
            .name(request.getName())
            .rated(request.getRated())
            .language(request.getLanguage())
            .largeImageURL(request.getLargeImageURL())
            .longDescription(request.getLongDescription())
            .releaseDate(request.getReleaseDate())
            .smallImageURl(request.getSmallImageURl())
            .trailerURL(request.getTrailerURL())
            .isShowing(request.getIsShowing())
            .build();
        return modelMapper.map(movieRepository.save(movie), MovieDto.class);
    }

    @Override
    public MovieDto updateMovie(UpdateMovieRequest request) {
        Movie movie = movieRepository.findById(request.getId()).orElseThrow(() -> new InternalException(ResponseCode.MOVIE_NOT_FOUND));
        if(request.getReleaseDate().isBefore(LocalDate.now())){
            throw new InternalException(ResponseCode.MOVIE_RELEASE_DATE_INVALID);
        }
        if(!request.getSmallImageURl().isBlank()) movie.setSmallImageURl(request.getSmallImageURl());
        if(!request.getShortDescription().isBlank()) movie.setShortDescription(request.getShortDescription());
        if(!request.getLongDescription().isBlank()) movie.setLongDescription(request.getLongDescription());
        if(!request.getLargeImageURL().isBlank()) movie.setLargeImageURL(request.getLargeImageURL());
        if(!request.getLanguage().isBlank()) movie.setLanguage(request.getLanguage());
        if(!request.getTrailerURL().isBlank()) movie.setTrailerURL(request.getTrailerURL());
        movie.setActors(request.getActors());
        movie.setDuration(request.getDuration());
        movie.setDirector(request.getDirector());
        movie.setIsShowing(request.getIsShowing());
        movie.setRated(request.getRated());
        movie.setName(request.getName());
        movie.setCategories(request.getCategories());
        movie.setReleaseDate(request.getReleaseDate());
        return modelMapper.map(movieRepository.save(movie), MovieDto.class);
    }


    @Override
    public List<MovieDto> findAllShowingMovies() {
        return movieRepository.findMoviesByIsShowingOrderByIdDesc(1)
            .stream()
            .map(movie -> modelMapper.map(movie, MovieDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public MovieDto getById(Integer movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new InternalException(ResponseCode.MOVIE_NOT_FOUND));
        return modelMapper.map(movie, MovieDto.class);
    }

    @Override
    public List<MovieDto> findAllShowingMoviesByName(String keyword) {
        return movieRepository.findMoviesByIsShowingAndNameContaining(1, keyword)
            .stream().map(movie -> modelMapper.map(movie, MovieDto.class))
            .collect(Collectors.toList());
    }

    @Override
    public Boolean deleteMovieById(Integer movieId) {
        try {
            movieRepository.findById(movieId).orElseThrow(() -> new InternalException(ResponseCode.MOVIE_NOT_FOUND));
            movieRepository.deleteById(movieId);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public PageResponse<MovieDto> getAllMovie(GetAllMovieRequest request) {
        Page<Movie> moviePage = movieRepository.findAll(request.getSpecification(), request.getPageable());
        return new PageResponse<>(moviePage.map(MovieDto::new));
    }

    @Override
    public List<MovieDto> findAllListMovies(GetAllMovieRequest request) {
        List<Movie> movieList = movieRepository.findAll(request.getSpecification());
        return movieList.stream().map(MovieDto::new).collect(Collectors.toList());
    }
}
