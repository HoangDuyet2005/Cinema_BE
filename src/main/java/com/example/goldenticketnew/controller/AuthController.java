package com.example.goldenticketnew.controller;


import com.example.goldenticketnew.model.RoleName;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.JwtAuthenticationResponse;
import com.example.goldenticketnew.payload.resquest.LoginRequest;
import com.example.goldenticketnew.payload.resquest.SignUpRequest;
import com.example.goldenticketnew.security.CurrentUser;
import com.example.goldenticketnew.security.UserPrincipal;
import com.example.goldenticketnew.service.auth.IAuthService;
import com.example.goldenticketnew.service.user.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Auth Controller", description = "Thao tác với auth")
public class AuthController {

    @Autowired
    IAuthService authService;

    @Autowired
    IUserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        return ResponseEntity.ok(new JwtAuthenticationResponse(authService.authenticateUser(loginRequest)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(authService.registerUser(signUpRequest, RoleName.ROLE_USER)).body(new ApiResponse(true, "User registered successfully"));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/registerStaff")
    public ResponseEntity<?> registerStaff(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userService.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.created(authService.registerUser(signUpRequest, RoleName.ROLE_STAFF)).body(new ApiResponse(true, "User registered successfully"));
    }
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@CurrentUser UserPrincipal currentUser , @Valid @RequestParam String newPassword, @Valid @RequestParam String oldPassword) {
        if(authService.changePassword(currentUser,newPassword,oldPassword))
            return new ResponseEntity(new ApiResponse(true, "Success! New password can be use right now!") , HttpStatus.OK);
        return new ResponseEntity(new ApiResponse(false, "Fail to change password!"),  HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
