package com.example.completion_project.model.dto.response.lesson_progress_res;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LessonProgressResponse {

    private Integer id;
    private Integer enrollmentId;
    private Integer lessonId;
    private String lessonTitle;
    private Boolean isCompleted;
    private LocalDateTime completedAt;
    private LocalDateTime lastAccessed;
}
