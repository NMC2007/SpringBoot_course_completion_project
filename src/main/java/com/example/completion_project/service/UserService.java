package com.example.completion_project.service;

import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.request.UserRequest.UpdateRoleRequest;
import com.example.completion_project.model.dto.request.UserRequest.UpdateStatusRequest;
import com.example.completion_project.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers(Role role, Boolean status);
    UserResponse getInfoUser(Integer id);
    UserResponse updateUserRole(Integer userId, UpdateRoleRequest req);
    UserResponse updateUserStatus(Integer userId, UpdateStatusRequest req);
    String deleteUserById(Integer userId);
}
