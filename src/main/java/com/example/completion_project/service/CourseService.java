package com.example.completion_project.service;

import com.example.completion_project.model.Enum.CourseStatus;
import com.example.completion_project.model.dto.request.courseRequest.CourseCreateRequest;
import com.example.completion_project.model.dto.request.courseRequest.UpdateStatusCourseRequest;
import com.example.completion_project.model.dto.response.CourseResponse.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses(CourseStatus status);
    CourseResponse createCourse(CourseCreateRequest req);
    CourseResponse updateCourseStatus(Integer courseId, UpdateStatusCourseRequest req);
}
