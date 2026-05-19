package com.example.completion_project.model.dto.response.courseRes;

import com.example.completion_project.model.enums.CourseStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CourseResponse {

    private Integer id;
    private String title;
    private String description;
    private String teacherUsername;
    private BigDecimal price;
    private Integer durationHours;
    private CourseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
