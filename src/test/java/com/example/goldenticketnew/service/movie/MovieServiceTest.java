package com.example.goldenticketnew.service.movie;

import com.example.goldenticketnew.dtos.MovieDto;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Movie;
import com.example.goldenticketnew.repository.IMovieRepository;
import com.example.goldenticketnew.repository.MovieRatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private IMovieRepository movieRepository;

    @Mock
    private MovieRatingRepository movieRatingRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private MovieService movieService;

    private Movie mockMovie;
    private MovieDto mockMovieDto;

    @BeforeEach
    void setUp() {
        mockMovie = new Movie();
        mockMovie.setId(1);
        mockMovie.setName("Avenger");
        mockMovie.setIsShowing(1);

        mockMovieDto = new MovieDto();
        mockMovieDto.setId(1);
        mockMovieDto.setName("Avenger");
    }

    @Test
    void getById_whenMovieExists_shouldReturnMovieDto() {
        // Given
        given(movieRepository.findById(1)).willReturn(Optional.of(mockMovie));
        given(modelMapper.map(mockMovie, MovieDto.class)).willReturn(mockMovieDto);
        given(movieRatingRepository.getAverageRatingByMovieId(1)).willReturn(4.5);
        given(movieRatingRepository.countRatingsByMovieId(1)).willReturn(100L);

        // When
        MovieDto result = movieService.getById(1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Avenger");
        assertThat(result.getAvgRating()).isEqualTo(4.5);
        assertThat(result.getTotalVotes()).isEqualTo(100L);
    }

    @Test
    void getById_whenMovieNotFound_shouldThrowInternalException() {
        // Given
        given(movieRepository.findById(99)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> movieService.getById(99))
                .isInstanceOf(InternalException.class);
    }

    @Test
    void findAllShowingMovies_whenMoviesExist_shouldReturnList() {
        // Given
        given(movieRepository.findMoviesByIsShowingOrderByIdDesc(1))
                .willReturn(Arrays.asList(mockMovie));
        given(modelMapper.map(mockMovie, MovieDto.class)).willReturn(mockMovieDto);

        // When
        List<MovieDto> result = movieService.findAllShowingMovies();

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Avenger");
    }

    @Test
    void findAllShowingMoviesByName_whenMatchesFound_shouldReturnList() {
        // Given
        given(movieRepository.findMoviesByIsShowingAndNameContaining(1, "Ave"))
                .willReturn(Arrays.asList(mockMovie));
        given(modelMapper.map(mockMovie, MovieDto.class)).willReturn(mockMovieDto);

        // When
        List<MovieDto> result = movieService.findAllShowingMoviesByName("Ave");

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Avenger");
    }
}
