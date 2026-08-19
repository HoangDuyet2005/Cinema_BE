package com.example.goldenticketnew.controller;


import com.example.goldenticketnew.dtos.UserDto;
import com.example.goldenticketnew.payload.GetAllUserRequest;
import com.example.goldenticketnew.payload.UserIdentityAvailability;
import com.example.goldenticketnew.payload.UserSummary;
import com.example.goldenticketnew.payload.response.ApiResponse;
import com.example.goldenticketnew.payload.response.PageResponse;
import com.example.goldenticketnew.payload.response.ResponseBase;
import com.example.goldenticketnew.payload.resquest.DenyContentCreatorRequest;
import com.example.goldenticketnew.payload.resquest.SendContentCreatorRequest;
import com.example.goldenticketnew.payload.resquest.UpdateCategoryRequest;
import com.example.goldenticketnew.payload.resquest.UpdateUserRequest;
import com.example.goldenticketnew.security.CurrentUser;
import com.example.goldenticketnew.security.UserPrincipal;
import com.example.goldenticketnew.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Thao tác với User")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(
        summary = "Lấy thông tin user hiện tại",
        description = "- Lấy thông tin user hiện tại đang đăng nhập"
    )
    @GetMapping("/me")
    public ResponseEntity<ResponseBase<UserSummary>> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return ResponseEntity.ok(new ResponseBase<> (userService.getCurrentUser(currentUser)));
    }

    @Operation(
        summary = "Get toàn bộ user",
        description = "- Get toàn bộ user"
    )
    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
//
    public ResponseEntity<ResponseBase<List<UserDto>>> getAllUser(  @ParameterObject GetAllUserRequest request) {

        return ResponseEntity.ok(new ResponseBase<>(userService.getAllUser(request)));
    }
    @Operation(
        summary = "Kiểm tra username",
        description = "- Kiểm tra xem username đã được sử dụng hay chưa"
    )
    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {

        return new UserIdentityAvailability(!userService.existsByUsername(username));
    }
    @Operation(
        summary = "Kiểm tra email",
        description = "- Kiểm tra xem email đã được sử dụng hay chưa"
    )
    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        return new UserIdentityAvailability(!userService.existsByEmail(email));
    }

    @Operation(
        summary = "Get chi tiết user",
        description = "- Get chi tiết user by username"
    )
    @GetMapping("/{username}")
    public ResponseEntity<ResponseBase<UserDto>> getUserProfile(@PathVariable(value = "username") String username) {
        return ResponseEntity.ok(new ResponseBase<>(userService.getUserProfile(username)));
    }

    //Edit user
    @Operation(
        summary = "Chỉnh sửa thông tin User",
        description = "- Chỉnh sửa thông tin User"
    )
    @PutMapping("/updateInfo")
    public ResponseEntity<ResponseBase<UserDto>>  updateInfoUser(@RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(new ResponseBase<>(userService.updateInfoUser(request)));
    }

    @Operation(
        summary = "Xóa một User",
        description = "- Xóa một User"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@Valid @PathVariable Long id) {
        if (userService.deleteUserById(id))
            return ResponseEntity.ok(new ApiResponse(true, "Delete User Successfully"));
        return ResponseEntity.ok(new ApiResponse(false, "Please check the id"));
    }



    @Operation(
        summary = "Get toàn bộ user đang xin quyền viết bài ",
        description = "- Get toàn bộ user đang xin quyền viết bài"
    )
    @GetMapping("/contenCreator/waiting/getAll")
//    @PreAuthorize("hasRole('ADMIN')")
//
    public ResponseEntity<ResponseBase<PageResponse<UserDto>>> getAllUserWaiting( @ParameterObject Pageable pageable, @ParameterObject GetAllUserRequest request) {
request.setPageable(pageable);
        return ResponseEntity.ok(new ResponseBase<>(userService.getListUserIsWaiting(request)));
    }

    @Operation(
        summary = "Get danh sach user xếp theo tổng số lượng bài viết của tháng",
        description = "- Get dashboard user"
    )
    @GetMapping("/dashBoard")
    public ResponseEntity<ResponseBase<List<UserDto>>> getUserReport(@Parameter String dateTime) {
        return ResponseEntity.ok(new ResponseBase<>(userService.getUserReport(dateTime)));
    }

}
