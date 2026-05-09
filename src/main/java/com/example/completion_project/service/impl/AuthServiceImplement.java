package com.example.completion_project.service.impl;

import com.example.completion_project.exception.BadCredentialsExceptionCustom;
import com.example.completion_project.exception.DuplicateResourceException;
import com.example.completion_project.model.dto.request.UserCreateDTO;
import com.example.completion_project.model.dto.request.UserLoginDTO;
import com.example.completion_project.model.dto.response.JwtResponse;
import com.example.completion_project.model.dto.response.UserResponse;
import com.example.completion_project.model.entity.User;
import com.example.completion_project.repository.UserRepository;
import com.example.completion_project.security.jwt.JwtProvider;
import com.example.completion_project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ModelMapper modelMapper;

    @Override
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
}
