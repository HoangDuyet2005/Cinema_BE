package com.example.goldenticketnew.service.auth;

import com.example.goldenticketnew.model.RoleName;
import com.example.goldenticketnew.payload.resquest.ChangePasswordRequest;
import com.example.goldenticketnew.payload.resquest.LoginRequest;
import com.example.goldenticketnew.payload.resquest.SignUpRequest;
import com.example.goldenticketnew.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public interface IAuthService {
    String authenticateUser(LoginRequest loginRequest);
    URI registerUser(SignUpRequest signUpRequest, RoleName roleName);

    Boolean changePassword(UserPrincipal currentUser, String newPassword, String oldPassword);

}
