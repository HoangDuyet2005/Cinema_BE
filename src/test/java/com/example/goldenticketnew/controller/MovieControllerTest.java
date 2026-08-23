package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.dtos.MovieDto;
import com.example.goldenticketnew.service.movie.IMovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.goldenticketnew.security.CustomUserDetailsService;
import com.example.goldenticketnew.security.JwtAuthenticationEntryPoint;
import com.example.goldenticketnew.security.JwtTokenProvider;
import com.example.goldenticketnew.repository.MovieRatingRepository;

@WebMvcTest(MovieController.class)
@AutoConfigureMockMvc(addFilters = false)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMovieService movieService;

    @MockBean
    private MovieRatingRepository movieRatingRepository;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;

    private MovieDto mockMovieDto;

    @BeforeEach
    void setUp() {
        mockMovieDto = new MovieDto();
        mockMovieDto.setId(1);
        mockMovieDto.setName("Avenger");
        mockMovieDto.setAvgRating(4.5);
    }

    @Test
    void findAllShowingMovies_shouldReturn200AndList() throws Exception {
        // Given
        List<MovieDto> movieList = Arrays.asList(mockMovieDto);
        given(movieService.findAllShowingMovies()).willReturn(movieList);

        // When & Then
        mockMvc.perform(get("/api/movies/showing")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Avenger"));
    }

    @Test
    void getMovieById_whenExists_shouldReturn200AndDto() throws Exception {
        // Given
        given(movieService.getById(1)).willReturn(mockMovieDto);

        // When & Then
        mockMvc.perform(get("/api/movies/details/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Avenger"));
    }

    @Test
    void findAllShowingMoviesByName_shouldReturn200AndList() throws Exception {
        // Given
        List<MovieDto> movieList = Arrays.asList(mockMovieDto);
        given(movieService.findAllShowingMoviesByName("Ave")).willReturn(movieList);

        // When & Then
        mockMvc.perform(get("/api/movies/showing/search")
                        .param("name", "Ave")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Avenger"));
    }
}
