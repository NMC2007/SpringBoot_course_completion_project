package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.AuthRequest.UserCreateDTO;
import com.example.completion_project.model.dto.request.AuthRequest.UserLoginDTO;
import com.example.completion_project.model.dto.request.AuthRequest.VerifyTokenRequest;
import com.example.completion_project.model.dto.request.AuthRequest.UpdatePasswordRequest;
import com.example.completion_project.model.dto.response.JwtResponse;
import com.example.completion_project.model.dto.response.UserResponse;
import com.example.completion_project.model.dto.response.VerifyTokenResponse;

public interface AuthService {

    UserResponse register(UserCreateDTO req);

    JwtResponse login(UserLoginDTO req);

    VerifyTokenResponse verifyToken(VerifyTokenRequest req);

    UserResponse getCurrentUser();

    String updateUserPassword(Integer userId, UpdatePasswordRequest req);
}
