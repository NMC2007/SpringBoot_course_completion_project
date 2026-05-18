package com.example.completion_project.service.impl;

import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.dto.request.lessonRequest.LessonCreateRequest;
import com.example.completion_project.model.dto.response.lessonResponse.LessonResponse;
import com.example.completion_project.model.entity.Course;
import com.example.completion_project.model.entity.Lesson;
import com.example.completion_project.repository.CourseRepository;
import com.example.completion_project.repository.LessonRepository;
import com.example.completion_project.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

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
}
