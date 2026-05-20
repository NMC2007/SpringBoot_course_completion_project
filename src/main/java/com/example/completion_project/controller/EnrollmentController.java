package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.dto.request.enrollment_req.EnrollmentCreateRequest;
import com.example.completion_project.model.dto.response.enrollment_res.EnrollmentResponse;
import com.example.completion_project.model.dto.response.lesson_progress_res.LessonProgressResponse;
import com.example.completion_project.service.EnrollmentService;
import com.example.completion_project.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;
    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<?> getMyEnrollments() {
        List<EnrollmentResponse> enrollments =
                enrollmentService.getMyEnrollments();

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        enrollments,
                        null,
                        200,
                        "Lấy danh sách khóa học đã đăng ký thành công"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createEnrollment(
            @RequestBody
            @Valid EnrollmentCreateRequest req
    ) {

        EnrollmentResponse enrollment =
                enrollmentService.createEnrollment(req);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        enrollment,
                        null,
                        201,
                        "Đăng ký khóa học thành công"
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/{enrollment_id}")
    public ResponseEntity<?> getEnrollmentDetail(
            @PathVariable("enrollment_id")
            Integer enrollmentId
    ) {

        EnrollmentResponse enrollment =
                enrollmentService.getEnrollmentDetail(enrollmentId);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        enrollment,
                        null,
                        200,
                        "Lấy thông tin đăng ký khóa học thành công"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{enrollment_id}/complete_lesson/{lesson_id}")
    public ResponseEntity<?> completeLesson(
            @PathVariable("enrollment_id")
            Integer enrollmentId,
            @PathVariable("lesson_id")
            Integer lessonId
    ) {

        LessonProgressResponse progress =
                lessonService.completeLesson(
                        enrollmentId,
                        lessonId
                );

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        progress,
                        null,
                        200,
                        "Cập nhật tiến trình bài học thành công"
                ),
                HttpStatus.OK
        );
    }
}
