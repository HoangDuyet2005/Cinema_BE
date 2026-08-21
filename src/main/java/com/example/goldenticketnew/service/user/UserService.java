package com.example.goldenticketnew.service.user;


import com.example.goldenticketnew.dtos.UserDto;
import com.example.goldenticketnew.enums.ResponseCode;
import com.example.goldenticketnew.exception.InternalException;
import com.example.goldenticketnew.exception.ResourceNotFoundException;
import com.example.goldenticketnew.model.Role;
import com.example.goldenticketnew.model.User;
import com.example.goldenticketnew.payload.GetAllUserRequest;
import com.example.goldenticketnew.payload.UserSummary;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.resquest.UpdateUserRequest;
import com.example.goldenticketnew.repository.IArticleRepository;
import com.example.goldenticketnew.repository.UserRepository;
import com.example.goldenticketnew.security.UserPrincipal;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private IArticleRepository articleRepository;


    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
    }

    @Override
    public User saveUser(User user) {
        return userRepository.saveAndFlush(user);
    }


    @Override
    public List<UserDto> getAllUser(GetAllUserRequest request) {
        int check = 0;
        List<User> notAdmin = userRepository.findAll(request.getSpecification());

        for (int i = notAdmin.size() - 1; i >= 0; i--) {
            for (Role element : notAdmin.get(i).getRoles()) {
                if (element.getId() == 2) {
                    check = 1;
                }
            }
            if (check == 1) {
                notAdmin.remove(i);
                check = 0;
            }

        }
        return notAdmin.stream().map(UserDto::new).collect(Collectors.toList());
    }
    @Override
    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(),currentUser.getEmail(), currentUser.getImage(), currentUser.getAuthorities().toString());
    }

    @Override
    public UserDto getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        return new UserDto(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);

    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDto updateInfoUser(UpdateUserRequest request) {
        User user = userRepository.findById(request.getId()).orElseThrow(() -> new InternalException(ResponseCode.USER_NOT_FOUND));
        if(request.getName() != null) user.setName(request.getName());
        if(request.getImage() != null)  user.setImage(request.getImage());
        if(request.getBio() != null) user.setBio(request.getBio());
        return new UserDto(userRepository.save(user));
    }

    @Override
    public Boolean deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;

    }

    @Override
    public List<UserDto> getUserReport(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTimeMY = LocalDate.parse(dateTime, formatter);
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(UserDto::new).collect(Collectors.toList());
        for(UserDto userDto : userDtos){
            userDto.setAmountArticle(articleRepository.getTotalArticleInUser(dateTimeMY.getYear(),dateTimeMY.getMonthValue(),userDto.getUsername()));

        }
        return userDtos.stream().sorted((u1, u2) -> Long.compare(u2.getAmountArticle() != null ? u2.getAmountArticle() : 0L, u1.getAmountArticle() != null ? u1.getAmountArticle() : 0L)).collect(Collectors.toList());
    }


    @Override
    public PageResponse<UserDto> getListUserIsWaiting(GetAllUserRequest request) {
        request.setIsContent(2);
        Page<User> users = userRepository.findAll(request.getSpecification(),request.getPageable());
        return new PageResponse<>(users.map(UserDto::new));
    }


}
