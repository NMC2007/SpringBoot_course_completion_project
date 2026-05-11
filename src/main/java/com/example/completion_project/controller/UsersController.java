package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.request.UserCreateDTO;
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
        return new ResponseEntity<>(MapToAPIResponse.mapTo(res, null, 201, "Thao tác thành công"), HttpStatus.CREATED);
    }
}
