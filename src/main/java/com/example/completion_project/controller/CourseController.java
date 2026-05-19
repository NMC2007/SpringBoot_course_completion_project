package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.enums.CourseStatus;
import com.example.completion_project.model.dto.request.courseReq.CourseCreateRequest;
import com.example.completion_project.model.dto.request.courseReq.UpdateCourseRequest;
import com.example.completion_project.model.dto.request.courseReq.UpdateStatusCourseRequest;
import com.example.completion_project.model.dto.response.courseRes.CourseInfoResponse;
import com.example.completion_project.model.dto.response.courseRes.CourseResponse;
import com.example.completion_project.model.dto.response.lessonRes.LessonResponse;
import com.example.completion_project.service.CourseService;
import com.example.completion_project.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<?> findCourses(
            @RequestParam(required = false)
            CourseStatus status,
            @RequestParam(required = false)
            String keyword,
            @RequestParam(required = false)
            Integer teacherId
    ) {
        List<CourseResponse> courses =
                courseService.getAllCourses(
                        status,
                        keyword,
                        teacherId
                );

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        courses,
                        null,
                        200,
                        "Lấy danh sách khóa học thành công"
                ),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<?> createCourse(
            @RequestBody
            @Valid
            CourseCreateRequest req
    ) {

        CourseResponse course =
                courseService.createCourse(req);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        course,
                        null,
                        201,
                        "Tạo khóa học thành công"
                ),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{course_id}/status")
    public ResponseEntity<?> updateCourseStatus(
            @PathVariable("course_id") Integer courseId,

            @RequestBody(required = false)
            @Valid
            UpdateStatusCourseRequest req
    ) {

        if (req == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        CourseResponse course =
                courseService.updateCourseStatus(
                        courseId,
                        req
                );

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        course,
                        null,
                        200,
                        "Cập nhật trạng thái khóa học thành công"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{course_id}")
    public ResponseEntity<?> getCourseDetail(
            @PathVariable("course_id")
            Integer courseId
    ) {
        CourseInfoResponse course = courseService.getPublishedCourseDetail(courseId);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        course,
                        null,
                        200,
                        "Lấy thông tin khóa học thành công"
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("/{course_id}/lessons")
    public ResponseEntity<?> getPublishedLessonsByCourse(
            @PathVariable("course_id")
            Integer courseId
    ) {

        List<LessonResponse> lessons =
                lessonService.getPublishedLessonsByCourse(
                        courseId
                );

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        lessons,
                        null,
                        200,
                        "Lấy danh sách bài học thành công"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{course_id}")
    public ResponseEntity<?> updateCourse(
            @PathVariable("course_id")
            Integer courseId,
            @RequestBody(required = false)
            @Valid UpdateCourseRequest req
    ) {

        if (req == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        CourseResponse course = courseService.updateCourse(courseId, req);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        course,
                        null,
                        200,
                        "Cập nhật khóa học thành công"
                ),
                HttpStatus.OK
        );
    }
}
