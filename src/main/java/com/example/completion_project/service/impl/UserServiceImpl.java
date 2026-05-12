package com.example.completion_project.service.impl;

import com.example.completion_project.exception.AccessDeniedExceptionCustom;
import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.request.UserRequest.UpdateRoleRequest;
import com.example.completion_project.model.dto.request.UserRequest.UpdateStatusRequest;
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
        User user = getUserById(id);

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserRole(Integer userId, UpdateRoleRequest req) {

        User user = getUserById(userId);

        if (user.getRole() == Role.ROLE_ADMIN) {
            throw new AccessDeniedExceptionCustom(
                    "Không thể cập nhật role cho ADMIN khác"
            );
        }

        user.setRole(req.getRole());

        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserStatus(Integer userId, UpdateStatusRequest req) {

        User user = getUserById(userId);

        if (user.getRole() == Role.ROLE_ADMIN) {
            throw new AccessDeniedExceptionCustom(
                    "Không thể cập nhật trạng thái của ADMIN khác"
            );
        }

        user.setIsActive(req.getIsActive());

        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponse.class);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUserById(Integer userId) {

        User user = getUserById(userId);

        if (user.getRole() == Role.ROLE_ADMIN) {
            throw new AccessDeniedExceptionCustom(
                    "Không thể xóa ADMIN khác"
            );
        }

        userRepository.delete(user);

        return "Xóa người dùng thành công";
    }

    private User getUserById(Integer userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy người dùng"));
    }
}
