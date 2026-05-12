package com.example.completion_project.model.dto.request.UserRequest;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusRequest {
    @NotNull(message = "Trạng thái không được để trống")
    private Boolean isActive;
}
