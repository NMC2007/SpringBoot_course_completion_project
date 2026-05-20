package com.example.completion_project.model.dto.request.user_req;

import com.example.completion_project.model.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRoleRequest {
    @NotNull(message = "Role không được để trống")
    private Role role;
}