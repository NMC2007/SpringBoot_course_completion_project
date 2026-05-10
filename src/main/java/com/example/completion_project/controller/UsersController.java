package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.response.UserResponse;
import com.example.completion_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {
    private final UserService userService;

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
}
