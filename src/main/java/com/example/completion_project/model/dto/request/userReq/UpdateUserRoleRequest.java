package com.example.completion_project.model.dto.request.userReq;

import com.example.completion_project.model.Enum.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequest {
    @NotNull(message = "Role không được để trống")
    private Role role;
}