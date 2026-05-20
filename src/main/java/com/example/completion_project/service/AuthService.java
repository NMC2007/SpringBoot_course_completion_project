package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.auth_req.UserCreateDTO;
import com.example.completion_project.model.dto.request.auth_req.UserLoginDTO;
import com.example.completion_project.model.dto.request.auth_req.VerifyTokenRequest;
import com.example.completion_project.model.dto.request.auth_req.UpdatePasswordRequest;
import com.example.completion_project.model.dto.response.auth_res.JwtResponse;
import com.example.completion_project.model.dto.response.user_res.UserResponse;
import com.example.completion_project.model.dto.response.auth_res.VerifyTokenResponse;

public interface AuthService {

    UserResponse register(UserCreateDTO req);

    JwtResponse login(UserLoginDTO req);

    VerifyTokenResponse verifyToken(VerifyTokenRequest req);

    UserResponse getCurrentUser();

    String updateUserPassword(Integer userId, UpdatePasswordRequest req);
}
