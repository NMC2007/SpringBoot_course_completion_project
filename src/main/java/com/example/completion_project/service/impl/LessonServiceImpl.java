package com.example.completion_project.service.impl;

import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.dto.request.lessonReq.LessonCreateRequest;
import com.example.completion_project.model.dto.request.lessonReq.UpdateLessonPublishRequest;
import com.example.completion_project.model.dto.request.lessonReq.UpdateLessonRequest;
import com.example.completion_project.model.dto.response.lessonRes.LessonInfoResponse;
import com.example.completion_project.model.dto.response.lessonRes.LessonResponse;
import com.example.completion_project.model.entity.Course;
import com.example.completion_project.model.entity.Lesson;
import com.example.completion_project.repository.CourseRepository;
import com.example.completion_project.repository.LessonRepository;
import com.example.completion_project.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;

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
}
