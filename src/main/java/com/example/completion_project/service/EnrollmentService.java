package com.example.completion_project.service;

import com.example.completion_project.model.dto.request.enrollment_req.EnrollmentCreateRequest;
import com.example.completion_project.model.dto.response.enrollment_res.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse createEnrollment(EnrollmentCreateRequest req);
    List<EnrollmentResponse> getMyEnrollments();
    EnrollmentResponse getEnrollmentDetail(Integer enrollmentId);
}
