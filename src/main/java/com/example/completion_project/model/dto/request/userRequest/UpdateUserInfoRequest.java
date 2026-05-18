package com.example.completion_project.model.dto.request.userRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserInfoRequest {

    @Size(min = 3, max = 50,
            message = "Username phải từ 3-50 ký tự")
    private String username;

    @Email(message = "Email không đúng định dạng")
    private String email;

    @Size(min = 2, max = 100,
            message = "Họ tên phải từ 2-100 ký tự")
    private String fullName;
}
