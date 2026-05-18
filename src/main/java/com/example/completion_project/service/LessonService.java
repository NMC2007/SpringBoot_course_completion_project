package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.lessonReq.LessonCreateRequest;
import com.example.completion_project.model.dto.response.lessonRes.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(Integer courseId, LessonCreateRequest req);
    List<LessonResponse> getPublishedLessonsByCourse(Integer courseId);
}
