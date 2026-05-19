package com.example.completion_project.service.impl;

import com.example.completion_project.exception.*;
import com.example.completion_project.model.enums.Role;
import com.example.completion_project.model.dto.request.authReq.UserCreateDTO;
import com.example.completion_project.model.dto.request.authReq.UserLoginDTO;
import com.example.completion_project.model.dto.request.authReq.VerifyTokenRequest;
import com.example.completion_project.model.dto.request.authReq.UpdatePasswordRequest;
import com.example.completion_project.model.dto.response.authRes.JwtResponse;
import com.example.completion_project.model.dto.response.userRes.UserResponse;
import com.example.completion_project.model.dto.response.authRes.VerifyTokenResponse;
import com.example.completion_project.model.entity.User;
import com.example.completion_project.repository.UserRepository;
import com.example.completion_project.security.jwt.JwtProvider;
import com.example.completion_project.security.userdetail.CustomUserDetails;
import com.example.completion_project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse register(UserCreateDTO req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new DuplicateResourceException("Email đã tồn tại");
        }
        if (userRepository.existsByUsername(req.getUsername())) {
            throw new DuplicateResourceException("Tên đăng nhập đã tồn tại");
        }
        User newUser = new User();

        newUser.setUsername(req.getUsername());
        newUser.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        newUser.setEmail(req.getEmail());
        newUser.setFullName(req.getFullName());
        newUser.setRole(req.getRole());



        return modelMapper.map(userRepository.save(newUser), UserResponse.class);
    }

    @Override
    public JwtResponse login(UserLoginDTO req) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(),
                            req.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsExceptionCustom("Username hoặc mật khẩu không đúng");
        }

        User user = userRepository.findUserByUsername(req.getUsername())
                .orElseThrow();

        JwtResponse res = JwtResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .accessToken(jwtProvider.generateAccessToken((UserDetails) authentication.getPrincipal()))
                .refreshToken(null)
                .expirationDate(new Date(new Date().getTime() + 15 * 60 * 1000))
                .build();

        return res;
    }

    @Override
    public VerifyTokenResponse verifyToken(VerifyTokenRequest req) {
        jwtProvider.validateToken(req.getToken());

        String username = jwtProvider.getUsernameFromToken(req.getToken());

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new JwtExceptionCustom("User không tồn tại"));

        return VerifyTokenResponse.builder()
                .valid(true)
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new BadCredentialsExceptionCustom("Không tìm thấy người dùng"));

        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    @PreAuthorize("hasAnyRole('OWNER','ADMIN')")
    public String updateUserPassword(Integer userId, UpdatePasswordRequest req) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails currentUser =
                (CustomUserDetails) authentication.getPrincipal();

        User actor = userRepository.findUserByUsername(
                currentUser.getUsername()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Không tìm thấy người thao tác"
                ));

        User targetUser = userRepository.getUserById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy người dùng"
                        ));

        if (
                actor.getRole() == Role.ROLE_OWNER
                        && targetUser.getRole() == Role.ROLE_OWNER
        ) {
            throw new AccessDeniedExceptionCustom(
                    "OWNER không thể cập nhật mật khẩu OWNER khác"
            );
        }

        if (
                actor.getRole() == Role.ROLE_ADMIN
                        &&
                        (
                                targetUser.getRole() == Role.ROLE_ADMIN
                                        || targetUser.getRole() == Role.ROLE_OWNER
                        )
        ) {
            throw new AccessDeniedExceptionCustom(
                    "ADMIN không thể cập nhật mật khẩu ADMIN hoặc OWNER"
            );
        }

        targetUser.setPasswordHash(
                passwordEncoder.encode(req.getNewPassword())
        );

        userRepository.save(targetUser);

        return "Cập nhật mật khẩu thành công";
    }
}
