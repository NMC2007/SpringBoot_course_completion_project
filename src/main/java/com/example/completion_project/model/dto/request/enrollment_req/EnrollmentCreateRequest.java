package com.example.completion_project.model.dto.request.enrollment_req;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrollmentCreateRequest {

    @NotNull(message = "Course id không được để trống")
    private Integer courseId;
}
