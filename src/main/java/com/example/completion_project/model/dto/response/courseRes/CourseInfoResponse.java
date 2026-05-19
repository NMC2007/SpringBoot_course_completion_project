package com.example.completion_project.model.dto.response.courseRes;

import com.example.completion_project.model.enums.CourseStatus;
import com.example.completion_project.model.dto.response.lessonRes.LessonInfoResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CourseInfoResponse {

    private Integer id;
    private String title;
    private String description;
    private String teacherUsername;
    private BigDecimal price;
    private Integer durationHours;
    private CourseStatus status;
    private List<LessonInfoResponse> lessons;
}