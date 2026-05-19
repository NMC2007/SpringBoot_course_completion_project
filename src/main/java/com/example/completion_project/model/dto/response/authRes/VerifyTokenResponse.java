package com.example.completion_project.model.dto.response.authRes;

import com.example.completion_project.model.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerifyTokenResponse {
    private Boolean valid;
    private String username;
    private Role role;
}
