package com.example.goldenticketnew.controller;

import com.example.goldenticketnew.payload.resquest.SignUpRequest;
import com.example.goldenticketnew.payload.resquest.LoginRequest;
import com.example.goldenticketnew.service.auth.IAuthService;
import com.example.goldenticketnew.service.user.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import com.example.goldenticketnew.security.CustomUserDetailsService;
import com.example.goldenticketnew.security.JwtAuthenticationEntryPoint;
import com.example.goldenticketnew.security.JwtTokenProvider;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false) // Tat Spring Security de tap trung test logic Controller
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IAuthService authService;

    @MockBean
    private IUserService userService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JpaMetamodelMappingContext jpaMappingContext;

    private SignUpRequest validRequest;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        validRequest = new SignUpRequest("John Doe", "johndoe", "john@gmail.com", "password123");
        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsernameOrEmail("johndoe");
        validLoginRequest.setPassword("password123");
    }

    @Test
    void registerUser_whenValidInput_shouldReturn201Created() throws Exception {
        // Given
        given(userService.existsByUsername(validRequest.getUsername())).willReturn(false);
        given(userService.existsByEmail(validRequest.getEmail())).willReturn(false);
        given(authService.registerUser(any(SignUpRequest.class), any())).willReturn(URI.create("http://localhost/api/users/johndoe"));

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated()) // Mong doi ma HTTP 201
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void registerUser_whenUsernameExists_shouldReturn400BadRequest() throws Exception {
        // Given
        given(userService.existsByUsername(validRequest.getUsername())).willReturn(true);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Username is already taken!"));
    }

    @Test
    void registerUser_whenEmailExists_shouldReturn400BadRequest() throws Exception {
        // Given
        given(userService.existsByUsername(validRequest.getUsername())).willReturn(false);
        given(userService.existsByEmail(validRequest.getEmail())).willReturn(true);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Email Address already in use!"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid-email", "test@", "@domain.com", "test @domain.com", "a-very-long-email-address-that-exceeds-forty-characters@gmail.com"})
    void registerUser_whenEmailInvalid_shouldReturn400BadRequest(String invalidEmail) throws Exception {
        // Given
        validRequest.setEmail(invalidEmail);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345", "this-password-is-way-too-long-over-20-chars"})
    void registerUser_whenPasswordInvalidLength_shouldReturn400BadRequest(String invalidPassword) throws Exception {
        // Given
        validRequest.setPassword(invalidPassword);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "ab", "abc", "this-name-is-extremely-long-and-exceeds-forty-characters-limit"})
    void registerUser_whenNameInvalid_shouldReturn400BadRequest(String invalidName) throws Exception {
        // Given
        validRequest.setName(invalidName);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"a", "ab", "this-username-is-longer-than-fifteen"})
    void registerUser_whenUsernameInvalid_shouldReturn400BadRequest(String invalidUsername) throws Exception {
        // Given
        validRequest.setUsername(invalidUsername);

        // When & Then
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void registerStaff_whenValidInput_shouldReturn201Created() throws Exception {
        // Given
        given(userService.existsByUsername(validRequest.getUsername())).willReturn(false);
        given(userService.existsByEmail(validRequest.getEmail())).willReturn(false);
        given(authService.registerUser(any(SignUpRequest.class), any())).willReturn(URI.create("http://localhost/api/users/johndoe"));

        // When & Then
        mockMvc.perform(post("/api/auth/registerStaff")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void authenticateUser_whenValidInput_shouldReturn200AndToken() throws Exception {
        // Given
        given(authService.authenticateUser(any(LoginRequest.class))).willReturn("mock-jwt-token");

        // When & Then
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("mock-jwt-token"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void authenticateUser_whenMissingUsername_shouldReturn400BadRequest(String invalidUsername) throws Exception {
        // Given
        validLoginRequest.setUsernameOrEmail(invalidUsername);

        // When & Then
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void authenticateUser_whenMissingPassword_shouldReturn400BadRequest(String invalidPassword) throws Exception {
        // Given
        validLoginRequest.setPassword(invalidPassword);

        // When & Then
        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validLoginRequest)))
                .andExpect(status().isBadRequest());
    }
}
