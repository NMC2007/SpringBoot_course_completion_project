package com.example.completion_project.service.impl;

import com.example.completion_project.exception.AccessDeniedExceptionCustom;
import com.example.completion_project.exception.DuplicateResourceException;
import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.enums.Role;
import com.example.completion_project.model.dto.request.userReq.UpdateUserRoleRequest;
import com.example.completion_project.model.dto.request.userReq.UpdateUserStatusRequest;
import com.example.completion_project.model.dto.request.userReq.UpdateUserInfoRequest;
import com.example.completion_project.model.dto.response.userRes.UserResponse;
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
    public UserResponse updateUserRole(Integer userId, UpdateUserRoleRequest req) {

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
    public UserResponse updateUserStatus(Integer userId, UpdateUserStatusRequest req) {

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

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse updateUserInfo(Integer userId, UpdateUserInfoRequest req) {
        // check body rỗng
        if (
                req.getUsername() == null
                        &&
                        req.getEmail() == null
                        &&
                        req.getFullName() == null
        ) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        User user = getUserById(userId);

        if (user.getRole() == Role.ROLE_ADMIN) {
            throw new AccessDeniedExceptionCustom(
                    "Không thể cập nhật ADMIN khác"
            );
        }

        // update username
        if (
                req.getUsername() != null
                        &&
                        !req.getUsername().isBlank()
        ) {

            boolean isDuplicateUsername =
                    userRepository.existsByUsername(req.getUsername());

            if (
                    isDuplicateUsername
                            &&
                            !user.getUsername().equals(req.getUsername())
            ) {
                throw new DuplicateResourceException(
                        "Username đã tồn tại"
                );
            }

            user.setUsername(req.getUsername());
        }

        // update email
        if (
                req.getEmail() != null
                        &&
                        !req.getEmail().isBlank()
        ) {

            boolean isDuplicateEmail =
                    userRepository.existsByEmail(req.getEmail());

            if (
                    isDuplicateEmail
                            &&
                            !user.getEmail().equals(req.getEmail())
            ) {
                throw new DuplicateResourceException(
                        "Email đã tồn tại"
                );
            }

            user.setEmail(req.getEmail());
        }

        // update fullname
        if (
                req.getFullName() != null
                        &&
                        !req.getFullName().isBlank()
        ) {
            user.setFullName(req.getFullName());
        }

        // updatedAt tự update do @UpdateTimestamp
        User updatedUser = userRepository.save(user);

        return modelMapper.map(updatedUser, UserResponse.class);
    }

    private User getUserById(Integer userId) {
        return userRepository.getUserById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Không tìm thấy người dùng"));
    }
}
