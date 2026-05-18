package com.example.completion_project.service;

import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.request.userRequest.UpdateUserRoleRequest;
import com.example.completion_project.model.dto.request.userRequest.UpdateUserStatusRequest;
import com.example.completion_project.model.dto.request.userRequest.UpdateUserInfoRequest;
import com.example.completion_project.model.dto.response.userResponsr.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers(Role role, Boolean status);
    UserResponse getInfoUser(Integer id);
    UserResponse updateUserRole(Integer userId, UpdateUserRoleRequest req);
    UserResponse updateUserStatus(Integer userId, UpdateUserStatusRequest req);
    String deleteUserById(Integer userId);
    UserResponse updateUserInfo(Integer userId, UpdateUserInfoRequest req);
}
