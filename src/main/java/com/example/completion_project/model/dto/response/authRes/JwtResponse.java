package com.example.completion_project.model.dto.response.authRes;

import com.example.completion_project.model.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private final String type = "Bearer";
    private String username;
    private Role role;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private Date expirationDate;
}
