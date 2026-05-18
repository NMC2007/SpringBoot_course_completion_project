package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.lessonRequest.LessonCreateRequest;
import com.example.completion_project.model.dto.response.lessonResponse.LessonResponse;

public interface LessonService {
    LessonResponse createLesson(Integer courseId, LessonCreateRequest req);
}
