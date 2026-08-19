package com.example.goldenticketnew.service.user;


import com.example.goldenticketnew.dtos.UserDto;
import com.example.goldenticketnew.dtos.UserReportDto;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.payload.GetAllUserRequest;
import com.example.goldenticketnew.payload.UserProfile;
import com.example.goldenticketnew.payload.UserSummary;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.DenyContentCreatorRequest;
import com.example.goldenticketnew.payload.resquest.SendContentCreatorRequest;
import com.example.goldenticketnew.payload.resquest.UpdateCategoryRequest;
import com.example.goldenticketnew.payload.resquest.UpdateUserRequest;
import com.example.goldenticketnew.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {

    User getUser(Long id);

    User saveUser(User user);
    List<UserDto> getAllUser(GetAllUserRequest request);
    UserSummary getCurrentUser(UserPrincipal currentUser) ;
    UserDto getUserProfile(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    UserDto updateInfoUser(UpdateUserRequest request);
    Boolean deleteUserById(Long Id);

    List<UserDto> getUserReport(String dataTime);





    PageResponse<UserDto> getListUserIsWaiting(GetAllUserRequest request);
}
