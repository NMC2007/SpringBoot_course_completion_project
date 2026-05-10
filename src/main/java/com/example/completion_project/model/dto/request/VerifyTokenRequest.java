package com.example.completion_project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyTokenRequest {
    @NotBlank(message = "Token không được để trống")
    private String token;
}