package com.example.completion_project.controller;

import com.example.completion_project.mapper.MapToAPIResponse;
import com.example.completion_project.model.dto.request.lesson_req.UpdateLessonPublishRequest;
import com.example.completion_project.model.dto.request.lesson_req.UpdateLessonRequest;
import com.example.completion_project.model.dto.response.lesson_res.LessonInfoResponse;
import com.example.completion_project.model.dto.response.lesson_res.LessonResponse;
import com.example.completion_project.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {
    private final LessonService lessonService;

    @GetMapping("/{lesson_id}")
    public ResponseEntity<?> getLessonDetail(
            @PathVariable("lesson_id")
            Integer lessonId
    ) {

        LessonInfoResponse lesson =
                lessonService.getLessonDetail(lessonId);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        lesson,
                        null,
                        200,
                        "Lấy thông tin bài học thành công"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{lesson_id}/publish")
    public ResponseEntity<?> updateLessonPublish(
            @PathVariable("lesson_id")
            Integer lessonId,
            @RequestBody(required = false)
            @Valid UpdateLessonPublishRequest req
    ) {

        if (req == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        LessonResponse lesson =
                lessonService.updateLessonPublish(
                        lessonId,
                        req
                );

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        lesson,
                        null,
                        200,
                        "Cập nhật trạng thái bài học thành công"
                ),
                HttpStatus.OK
        );
    }

    @PutMapping("/{lesson_id}")
    public ResponseEntity<?> updateLesson(
            @PathVariable("lesson_id")
            Integer lessonId,
            @RequestBody(required = false)
            @Valid UpdateLessonRequest req
    ) {

        if (req == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        LessonResponse lesson =
                lessonService.updateLesson(
                        lessonId,
                        req
                );

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        lesson,
                        null,
                        200,
                        "Cập nhật bài học thành công"
                ),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/{lesson_id}")
    public ResponseEntity<?> DeleteLesson(
            @PathVariable("lesson_id")
            Integer lessonId
    ) {

        String message =
                lessonService.deleteLessonById(lessonId);

        return new ResponseEntity<>(
                MapToAPIResponse.mapTo(
                        message,
                        null,
                        200,
                        "Xoá người dùng thành công"
                ),
                HttpStatus.OK
        );
    }
}
