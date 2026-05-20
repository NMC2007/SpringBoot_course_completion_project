package com.example.completion_project.model.dto.response.enrollment_res;

import com.example.completion_project.model.enums.EnrollmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EnrollmentResponse {

    private Integer id;
    private Integer studentId;
    private String studentUsername;
    private Integer courseId;
    private String courseTitle;
    private LocalDateTime enrollmentDate;
    private EnrollmentStatus status;
    private LocalDateTime completionDate;
    private BigDecimal progressPercent;
}
