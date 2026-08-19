package com.example.goldenticketnew.service.auth;

import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.AppException;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.model.Role;
import com.example.goldenticketnew.model.RoleName;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.payload.resquest.LoginRequest;
import com.example.goldenticketnew.payload.resquest.SignUpRequest;
import com.example.goldenticketnew.repository.IRoleRepository;
import com.example.goldenticketnew.repository.UserRepository;
import com.example.goldenticketnew.security.JwtTokenProvider;
import com.example.goldenticketnew.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;

@Service
public class AuthService implements IAuthService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String authenticateUser(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.generateToken(authentication); //jwt
        } catch (Exception e){
            throw new InternalException(ResponseCode.LOGIN_FAIL);
        }

    }

    @Override
    public URI registerUser(SignUpRequest signUpRequest, RoleName roleName) {
        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
            signUpRequest.getEmail(), signUpRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(roleName)
            .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        //return URI
        return ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/users/{username}")
            .buildAndExpand(result.getUsername()).toUri();
    }

    @Override
    public Boolean changePassword(UserPrincipal currentUser, String newPassword, String oldPassword) {
        User user = userRepository.findById(currentUser.getId()).orElseThrow(() -> new InternalException(ResponseCode.FAILED));
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                oldPassword
            )
        );

        String a = passwordEncoder.encode(newPassword);
        String b = user.getPassword();
        if(!authentication.isAuthenticated()){
            throw new AppException(
                "Password invalid");
        }
        if(oldPassword.equals(newPassword)){
            throw new AppException(
                "Password must not be the same as the old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return true;
    }


}
