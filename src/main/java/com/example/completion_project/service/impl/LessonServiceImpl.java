package com.example.completion_project.service.impl;

import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.dto.request.lesson_req.LessonCreateRequest;
import com.example.completion_project.model.dto.request.lesson_req.UpdateLessonPublishRequest;
import com.example.completion_project.model.dto.request.lesson_req.UpdateLessonRequest;
import com.example.completion_project.model.dto.response.lesson_progress_res.LessonProgressResponse;
import com.example.completion_project.model.dto.response.lesson_res.LessonInfoResponse;
import com.example.completion_project.model.dto.response.lesson_res.LessonResponse;
import com.example.completion_project.model.entity.*;
import com.example.completion_project.repository.*;
import com.example.completion_project.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    private final ModelMapper modelMapper;

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public LessonResponse createLesson(Integer courseId, LessonCreateRequest req) {

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy khóa học"
                        ));

        Lesson lesson = new Lesson();

        lesson.setCourse(course);
        lesson.setTitle(req.getTitle());
        lesson.setContentUrl(req.getContentUrl());
        lesson.setTextContent(req.getTextContent());
        lesson.setOrderIndex(req.getOrderIndex());
        lesson.setIsPublished(
                req.getIsPublished() != null
                        ? req.getIsPublished()
                        : false
        );

        Lesson savedLesson =
                lessonRepository.save(lesson);

        LessonResponse res =
                modelMapper.map(
                        savedLesson,
                        LessonResponse.class
                );

        res.setCourseId(course.getId());

        res.setCourseTitle(course.getTitle());

        return res;
    }

    @Override
    public List<LessonResponse> getPublishedLessonsByCourse(Integer courseId) {
        // check course tồn tại
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy khóa học"
                        ));

        List<Lesson> lessons = lessonRepository.findPublishedLessonsByCourse(courseId);

        return lessons.stream()
                .map(lesson -> {
                    LessonResponse res =
                            modelMapper.map(
                                    lesson,
                                    LessonResponse.class
                            );

                    res.setCourseId(course.getId());
                    res.setCourseTitle(course.getTitle());

                    return res;
                })
                .toList();
    }

    @Override
    public LessonInfoResponse getLessonDetail(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy bài học"
                        ));

        // check publish
        if (!lesson.getIsPublished()) {
            throw new ResourceNotFoundException(
                    "Bài học chưa được công bố"
            );
        }

        return modelMapper.map(
                lesson,
                LessonInfoResponse.class
        );
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public LessonResponse updateLessonPublish(
            Integer lessonId,
            UpdateLessonPublishRequest req
    ) {

        if (req == null || req.getIsPublished() == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy bài học"
                        ));

        lesson.setIsPublished(req.getIsPublished());

        Lesson updatedLesson =
                lessonRepository.save(lesson);

        LessonResponse res =
                modelMapper.map(
                        updatedLesson,
                        LessonResponse.class
                );

        res.setCourseId(
                updatedLesson.getCourse().getId()
        );

        res.setCourseTitle(
                updatedLesson.getCourse().getTitle()
        );

        return res;
    }

    @Override
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public LessonResponse updateLesson(
            Integer lessonId,
            UpdateLessonRequest req
    ) {
        // check body rỗng
        if (
                req.getTitle() == null
                        &&
                        req.getContentUrl() == null
                        &&
                        req.getTextContent() == null
                        &&
                        req.getOrderIndex() == null
        ) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy bài học"
                        ));

        // update title
        if (
                req.getTitle() != null
                        &&
                        !req.getTitle().isBlank()
        ) {
            lesson.setTitle(req.getTitle());
        }

        // update content url
        if (
                req.getContentUrl() != null
                        &&
                        !req.getContentUrl().isBlank()
        ) {
            lesson.setContentUrl(req.getContentUrl());
        }

        // update text content
        if (
                req.getTextContent() != null
                        &&
                        !req.getTextContent().isBlank()
        ) {
            lesson.setTextContent(req.getTextContent());
        }

        // update order index
        if (req.getOrderIndex() != null) {
            lesson.setOrderIndex(req.getOrderIndex());
        }

        // updatedAt tự update do @UpdateTimestamp
        Lesson updatedLesson =
                lessonRepository.save(lesson);

        LessonResponse res =
                modelMapper.map(
                        updatedLesson,
                        LessonResponse.class
                );

        res.setCourseId(
                updatedLesson.getCourse().getId()
        );

        res.setCourseTitle(
                updatedLesson.getCourse().getTitle()
        );

        return res;
    }

    @Override
    public String deleteLessonById(Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy bài học"
                        ));

        lessonRepository.delete(lesson);

        return "Xóa bài học thành công";
    }

    @Override
    public LessonProgressResponse completeLesson(Integer enrollmentId, Integer lessonId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User student = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy học sinh"
                        ));

//        tìm đăng ký khoá học của học sinh hiện tại
        Enrollment enrollment =
                enrollmentRepository.findByIdAndStudentId(
                                enrollmentId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Không tìm thấy đăng ký khóa học"
                                ));

//        tìm bài học
        Lesson lesson = lessonRepository
                .findById(lessonId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy bài học"
                        ));

        // check lesson thuộc course của enrollment
        if (
                !lesson.getCourse().getId()
                        .equals(
                                enrollment.getCourse().getId()
                        )
        ) {
            throw new ResourceNotFoundException(
                    "Bài học không thuộc khóa học đã đăng ký"
            );
        }

        LessonProgress lessonProgress = lessonProgressRepository
                .findLessonProgress(enrollmentId, lessonId)
                .orElseGet(() -> {
                    LessonProgress progress =
                            new LessonProgress();

                    progress.setEnrollment(enrollment);
                    progress.setLesson(lesson);

                    return progress;
                });

        lessonProgress.setIsCompleted(true);
        lessonProgress.setCompletedAt(LocalDateTime.now());

        LessonProgress savedProgress =
                lessonProgressRepository.save(
                        lessonProgress
                );

        // update progressPercent
        long completedLessons = lessonProgressRepository
                .countCompletedLessons(enrollmentId);

        long totalLessons =
                lessonRepository.countByCourseId(
                        enrollment.getCourse().getId()
                );

        BigDecimal progressPercent =
                BigDecimal.valueOf(completedLessons)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(
                                BigDecimal.valueOf(totalLessons),
                                2,
                                RoundingMode.HALF_UP
                        );

        enrollment.setProgressPercent(
                progressPercent
        );

        enrollmentRepository.save(enrollment);

        LessonProgressResponse res =
                modelMapper.map(
                        savedProgress,
                        LessonProgressResponse.class
                );

        res.setEnrollmentId(enrollment.getId());
        res.setLessonId(lesson.getId());
        res.setLessonTitle(lesson.getTitle());

        return res;
    }
}
