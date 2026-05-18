package com.example.completion_project.model.dto.request.courseReq;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CourseCreateRequest {

    @NotBlank(message = "Tiêu đề không được để trống")
    private String title;

    private String description;

    @NotBlank(message = "Username giáo viên không được để trống")
    private String teacherUsername;

    @NotNull(message = "Giá khóa học không được để trống")
    @DecimalMin(value = "0.0", message = "Giá khóa học phải >= 0")
    private BigDecimal price;

    @NotNull(message = "Số giờ học không được để trống")
    @Min(value = 1, message = "Số giờ học phải >= 1")
    private Integer durationHours;
}