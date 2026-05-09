package com.example.completion_project.controller;

import com.example.completion_project.model.dto.request.UserCreateDTO;
import com.example.completion_project.model.dto.request.UserLoginDTO;
import com.example.completion_project.model.dto.response.UserResponse;
import com.example.completion_project.model.entity.User;
import com.example.completion_project.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping
    public String test() {
        return "Auth API public OK";
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO req) {
        UserResponse res = authService.register(req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
