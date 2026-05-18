package com.example.completion_project.service;

import com.example.completion_project.model.Enum.CourseStatus;
import com.example.completion_project.model.dto.request.courseReq.CourseCreateRequest;
import com.example.completion_project.model.dto.request.courseReq.UpdateCourseRequest;
import com.example.completion_project.model.dto.request.courseReq.UpdateStatusCourseRequest;
import com.example.completion_project.model.dto.response.courseRes.CourseInfoResponse;
import com.example.completion_project.model.dto.response.courseRes.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses(CourseStatus status);
    CourseResponse createCourse(CourseCreateRequest req);
    CourseResponse updateCourseStatus(Integer courseId, UpdateStatusCourseRequest req);
    CourseInfoResponse getPublishedCourseDetail(Integer courseId);
    CourseResponse updateCourse(Integer courseId, UpdateCourseRequest req);
}
