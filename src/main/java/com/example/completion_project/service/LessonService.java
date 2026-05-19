package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.lessonReq.LessonCreateRequest;
import com.example.completion_project.model.dto.request.lessonReq.UpdateLessonPublishRequest;
import com.example.completion_project.model.dto.request.lessonReq.UpdateLessonRequest;
import com.example.completion_project.model.dto.response.lessonRes.LessonInfoResponse;
import com.example.completion_project.model.dto.response.lessonRes.LessonResponse;

import java.util.List;

public interface LessonService {
    LessonResponse createLesson(Integer courseId, LessonCreateRequest req);
    List<LessonResponse> getPublishedLessonsByCourse(Integer courseId);
    LessonInfoResponse getLessonDetail(Integer lessonId);
    LessonResponse updateLessonPublish(Integer lessonId, UpdateLessonPublishRequest req);
    LessonResponse updateLesson(Integer lessonId, UpdateLessonRequest req);
}
