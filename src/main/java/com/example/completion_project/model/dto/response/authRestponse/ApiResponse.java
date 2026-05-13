package com.example.completion_project.model.dto.response.authRestponse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ApiResponse {
    private int status;
    private String message;
    private Object data;
    private Object errors;
    private LocalDateTime timestamp;
}
