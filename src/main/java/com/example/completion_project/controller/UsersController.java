package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.request.AuthRequest.UpdatePasswordRequest;
import com.example.completion_project.model.dto.request.UserRequest.UpdateRoleRequest;
import com.example.completion_project.model.dto.request.AuthRequest.UserCreateDTO;
import com.example.completion_project.model.dto.request.UserRequest.UpdateStatusRequest;
import com.example.completion_project.model.dto.response.UserResponse;
import com.example.completion_project.service.AuthService;
import com.example.completion_project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) Boolean status
    ) {
        List<UserResponse> users = userService.getAllUsers(role, status);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        users,
                        null,
                        200,
                        "Thao tác thành công"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUserById(
            @PathVariable("user_id") Integer userId
    ) {
        UserResponse user = userService.getInfoUser(userId);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        user,
                        null,
                        200,
                        "Thao tác thành công"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO req) {
        UserResponse res = authService.register(req);
        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        res,
                        null,
                        201,
                        "Thao tác thành công"
                ),
                HttpStatus.CREATED);
    }

    @PutMapping("/{user_id}/role")
    public ResponseEntity<?> updateUserRole(
            @PathVariable("user_id") Integer userId,
            @Valid @RequestBody UpdateRoleRequest req
    ) {

        UserResponse user = userService.updateUserRole(userId, req);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        user,
                        null,
                        200,
                        "Cập nhật role thành công"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{user_id}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable("user_id") Integer userId,
            @Valid @RequestBody UpdateStatusRequest req
    ) {

        UserResponse user =
                userService.updateUserStatus(userId, req);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        user,
                        null,
                        200,
                        "Cập nhật trạng thái tài khoản thành công"
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable("user_id") Integer userId
    ) {

        String message = userService.deleteUserById(userId);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        message,
                        null,
                        200,
                        "Xóa người dùng thành công"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{user_id}/password")
    public ResponseEntity<?> updateUserPassword(
            @PathVariable("user_id") Integer userId,
            @Valid @RequestBody UpdatePasswordRequest req
    ) {

        String message =
                authService.updateUserPassword(userId, req);;

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        message,
                        null,
                        200,
                        "Cập nhật mật khẩu thành công"
                ),
                HttpStatus.OK
        );
    }
}
