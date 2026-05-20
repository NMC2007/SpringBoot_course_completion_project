package com.example.completion_project.model.dto.response.user_res;

import com.example.completion_project.model.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private Role role;
    private Boolean isActive;
}
