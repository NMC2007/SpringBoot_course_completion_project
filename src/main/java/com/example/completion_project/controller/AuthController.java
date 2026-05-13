package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.dto.request.AuthRequest.UserLoginDTO;
import com.example.completion_project.model.dto.request.AuthRequest.VerifyTokenRequest;
import com.example.completion_project.model.dto.response.authRestponse.JwtResponse;
import com.example.completion_project.model.dto.response.userResponsr.UserResponse;
import com.example.completion_project.model.dto.response.authRestponse.VerifyTokenResponse;
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

    @GetMapping("/verify")
    public ResponseEntity<?> verifyToken(@Valid @RequestBody VerifyTokenRequest req) {
        VerifyTokenResponse res = authService.verifyToken(req);
        return new ResponseEntity<>(MapToAPIResponse.mapTo(res, null, 200, "Thao tác thành công"), HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        UserResponse res = authService.getCurrentUser();
        return new ResponseEntity<>(MapToAPIResponse.mapTo(res, null, 200, "Thao tác thành công"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO req) {
        JwtResponse res = authService.login(req);
        return new ResponseEntity<>(MapToAPIResponse.mapTo(res, null, 200, "Đăng nhập thành công"), HttpStatus.OK);
    }
}
