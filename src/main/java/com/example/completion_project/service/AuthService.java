package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.authReq.UserCreateDTO;
import com.example.completion_project.model.dto.request.authReq.UserLoginDTO;
import com.example.completion_project.model.dto.request.authReq.VerifyTokenRequest;
import com.example.completion_project.model.dto.request.authReq.UpdatePasswordRequest;
import com.example.completion_project.model.dto.response.authRes.JwtResponse;
import com.example.completion_project.model.dto.response.userRes.UserResponse;
import com.example.completion_project.model.dto.response.authRes.VerifyTokenResponse;

public interface AuthService {

    UserResponse register(UserCreateDTO req);

    JwtResponse login(UserLoginDTO req);

    VerifyTokenResponse verifyToken(VerifyTokenRequest req);

    UserResponse getCurrentUser();

    String updateUserPassword(Integer userId, UpdatePasswordRequest req);
}
