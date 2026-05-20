package com.example.completion_project.service;

import com.example.completion_project.model.enums.CourseStatus;
import com.example.completion_project.model.dto.request.course_req.CourseCreateRequest;
import com.example.completion_project.model.dto.request.course_req.UpdateCourseRequest;
import com.example.completion_project.model.dto.request.course_req.UpdateStatusCourseRequest;
import com.example.completion_project.model.dto.response.course_res.CourseInfoResponse;
import com.example.completion_project.model.dto.response.course_res.CourseResponse;

import java.util.List;

public interface CourseService {
    List<CourseResponse> getAllCourses(CourseStatus status, String keyword, Integer teacherId);
    CourseResponse createCourse(CourseCreateRequest req);
    CourseResponse updateCourseStatus(Integer courseId, UpdateStatusCourseRequest req);
    CourseInfoResponse getPublishedCourseDetail(Integer courseId);
    CourseResponse updateCourse(Integer courseId, UpdateCourseRequest req);
    String deleteCourse(Integer courseId);
}
