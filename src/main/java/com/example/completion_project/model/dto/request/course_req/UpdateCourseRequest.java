package com.example.completion_project.model.dto.request.course_req;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateCourseRequest {

    @Size(
            max = 255,
            message = "Tiêu đề không được vượt quá 255 ký tự"
    )
    private String title;

    private String description;

    @Positive(
            message = "Teacher ID phải lớn hơn 0"
    )
    private Integer teacherId;

    @DecimalMin(
            value = "0.0",
            inclusive = true,
            message = "Giá khóa học không được âm"
    )
    private BigDecimal price;

    @Positive(
            message = "Thời lượng khóa học phải lớn hơn 0"
    )
    private Integer durationHours;
}
