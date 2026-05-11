package com.example.completion_project.service.impl;

import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.response.UserResponse;
import com.example.completion_project.model.entity.User;
import com.example.completion_project.repository.UserRepository;
import com.example.completion_project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers(Role role, Boolean status) {
        return userRepository.findUsersWithFilter(role, status)
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getInfoUser(Integer id) {
        User user = userRepository
                .getInfoUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));

        return modelMapper.map(user, UserResponse.class);
    }
}
