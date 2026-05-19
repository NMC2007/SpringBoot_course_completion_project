package com.example.completion_project.model.dto.request.courseReq;

import com.example.completion_project.model.enums.CourseStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateStatusCourseRequest {

    @NotNull(message = "Trạng thái khóa học không được để trống")
    private CourseStatus status;
}
