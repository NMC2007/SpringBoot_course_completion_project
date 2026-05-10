package com.example.completion_project.service;

import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers(Role role, Boolean status);
}
