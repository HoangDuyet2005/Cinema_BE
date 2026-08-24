package com.example.goldenticketnew.service.auth;

import com.example.goldenticketnew.exception.AppException;
import com.example.goldenticketnew.model.Role;
import com.example.goldenticketnew.model.RoleName;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.payload.resquest.SignUpRequest;
import com.example.goldenticketnew.repository.IRoleRepository;
import com.example.goldenticketnew.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.goldenticketnew.payload.resquest.LoginRequest;
import com.example.goldenticketnew.security.JwtTokenProvider;
import com.example.goldenticketnew.exception.InternalException;

import java.net.URI;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider tokenProvider;

    @InjectMocks
    private AuthService authService;

    private SignUpRequest validRequest;
    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        // Arrange chung cho request hop le
        validRequest = new SignUpRequest("John Doe", "johndoe", "john@gmail.com", "password123");

        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsernameOrEmail("johndoe");
        validLoginRequest.setPassword("password123");

        // Set up context cho ServletUriComponentsBuilder vi no duoc dung trong code cua AuthService
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    void registerUser_whenValidInput_shouldSaveUserAndReturnUri() {
        // Given
        Role userRole = new Role(RoleName.ROLE_USER);
        User savedUser = new User("John Doe", "johndoe", "john@gmail.com", "encodedPassword");
        savedUser.setId(1L);

        given(passwordEncoder.encode(validRequest.getPassword())).willReturn("encodedPassword");
        given(roleRepository.findByName(RoleName.ROLE_USER)).willReturn(Optional.of(userRole));
        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // When
        URI resultUri = authService.registerUser(validRequest, RoleName.ROLE_USER);

        // Then
        // 1. Kiem tra URI tra ve
        assertThat(resultUri).isNotNull();
        assertThat(resultUri.toString()).contains("/users/johndoe");

        // 2. Kiem tra xem PasswordEncoder da duoc goi de ma hoa hay chua
        verify(passwordEncoder).encode("password123");

        // 3. Captor kiem tra xem Entity truyen vao userRepository.save() co dung thong tin hay khong (Side effect verification)
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        
        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser.getUsername()).isEqualTo(validRequest.getUsername());
        assertThat(capturedUser.getEmail()).isEqualTo(validRequest.getEmail());
        assertThat(capturedUser.getPassword()).isEqualTo("encodedPassword"); // Phai la pass da ma hoa
        assertThat(capturedUser.getRoles()).contains(userRole);
    }

    @Test
    void registerUser_whenRoleNotFound_shouldThrowAppException() {
        // Given
        given(passwordEncoder.encode(any())).willReturn("encodedPassword");
        given(roleRepository.findByName(RoleName.ROLE_USER)).willReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> authService.registerUser(validRequest, RoleName.ROLE_USER))
                .isInstanceOf(AppException.class)
                .hasMessageContaining("User Role not set.");
    }

    @Test
    void authenticateUser_whenValidCredentials_shouldReturnToken() {
        // Given
        Authentication authentication = mock(Authentication.class);
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(authentication);
        given(tokenProvider.generateToken(authentication)).willReturn("mock-jwt-token");

        // When
        String token = authService.authenticateUser(validLoginRequest);

        // Then
        assertThat(token).isEqualTo("mock-jwt-token");
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(tokenProvider).generateToken(authentication);
    }

    @Test
    void authenticateUser_whenInvalidCredentials_shouldThrowInternalException() {
        // Given
        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willThrow(new RuntimeException("Bad credentials"));

        // When & Then
        assertThatThrownBy(() -> authService.authenticateUser(validLoginRequest))
                .isInstanceOf(InternalException.class);
    }
}
