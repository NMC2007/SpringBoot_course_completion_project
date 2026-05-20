package com.example.completion_project.mapper;

import com.example.completion_project.model.dto.response.auth_res.ApiResponse;

import java.time.LocalDateTime;

public class MapToAPIResponse {
    public static ApiResponse mapTo(Object data, Object errors, int status, String message) {
        return ApiResponse.builder()
                .status(status)
                .message(message)
                .data(data)
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }

}
