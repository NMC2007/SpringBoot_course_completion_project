package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.enums.Role;
import com.example.completion_project.model.dto.request.auth_req.UpdatePasswordRequest;
import com.example.completion_project.model.dto.request.lesson_req.LessonCreateRequest;
import com.example.completion_project.model.dto.request.user_req.UpdateUserRoleRequest;
import com.example.completion_project.model.dto.request.auth_req.UserCreateDTO;
import com.example.completion_project.model.dto.request.user_req.UpdateUserStatusRequest;
import com.example.completion_project.model.dto.request.user_req.UpdateUserInfoRequest;
import com.example.completion_project.model.dto.response.lesson_res.LessonResponse;
import com.example.completion_project.model.dto.response.user_res.UserResponse;
import com.example.completion_project.service.AuthService;
import com.example.completion_project.service.LessonService;
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
    private final LessonService lessonService;
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
            @Valid @RequestBody UpdateUserRoleRequest req
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
            @Valid @RequestBody UpdateUserStatusRequest req
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

    @PutMapping("/{user_id}")
    public ResponseEntity<?> updateUserInfo(
            @PathVariable("user_id") Integer userId,
            @RequestBody(required = false)
            @Valid UpdateUserInfoRequest req
    ) {

        if (req == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        UserResponse user =
                userService.updateUserInfo(userId, req);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        user,
                        null,
                        200,
                        "Cập nhật thông tin thành công"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping("/{course_id}/lessons")
    public ResponseEntity<?> createLesson(
            @PathVariable("course_id")
            Integer courseId,
            @RequestBody @Valid LessonCreateRequest req
    ) {

        LessonResponse lesson =
                lessonService.createLesson(courseId, req);

        return new ResponseEntity<>(

                MapToAPIResponse.mapTo(
                        lesson,
                        null,
                        201,
                        "Tạo bài học thành công"
                ),

                HttpStatus.CREATED
        );
    }
}
