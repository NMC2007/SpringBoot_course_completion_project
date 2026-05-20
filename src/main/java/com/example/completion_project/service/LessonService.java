package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.lesson_req.LessonCreateRequest;
import com.example.completion_project.model.dto.request.lesson_req.UpdateLessonPublishRequest;
import com.example.completion_project.model.dto.request.lesson_req.UpdateLessonRequest;
import com.example.completion_project.model.dto.response.lesson_progress_res.LessonProgressResponse;
import com.example.completion_project.model.dto.response.lesson_res.LessonInfoResponse;
import com.example.completion_project.model.dto.response.lesson_res.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(Integer courseId, LessonCreateRequest req);
    List<LessonResponse> getPublishedLessonsByCourse(Integer courseId);
    LessonInfoResponse getLessonDetail(Integer lessonId);
    LessonResponse updateLessonPublish(Integer lessonId, UpdateLessonPublishRequest req);
    LessonResponse updateLesson(Integer lessonId, UpdateLessonRequest req);
    String deleteLessonById(Integer lessonId);
    LessonProgressResponse completeLesson(Integer enrollmentId, Integer lessonId);
}
