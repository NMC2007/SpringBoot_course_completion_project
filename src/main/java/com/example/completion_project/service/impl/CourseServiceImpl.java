package com.example.completion_project.service.impl;

import com.example.completion_project.exception.AccessDeniedExceptionCustom;
import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.Enum.CourseStatus;
import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.dto.request.courseRequest.CourseCreateRequest;
import com.example.completion_project.model.dto.request.courseRequest.UpdateStatusCourseRequest;
import com.example.completion_project.model.dto.response.CourseResponse.CourseResponse;
import com.example.completion_project.model.entity.Course;
import com.example.completion_project.model.entity.User;
import com.example.completion_project.repository.CourseRepository;
import com.example.completion_project.repository.UserRepository;
import com.example.completion_project.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<CourseResponse> getAllCourses(CourseStatus status) {

        List<Course> courses = courseRepository.findAllCourses(status);

        return courses.stream()
                .map(course -> {

                    CourseResponse res =
                            modelMapper.map(
                                    course,
                                    CourseResponse.class
                            );

                    res.setTeacherUsername(
                            course.getTeacher().getUsername()
                    );

                    return res;
                })
                .toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponse createCourse(CourseCreateRequest req) {

        User teacher = userRepository
                .findUserByUsername(req.getTeacherUsername())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy giáo viên"
                        ));

        // check role teacher
        if (teacher.getRole() != Role.ROLE_TEACHER) {
            throw new AccessDeniedExceptionCustom(
                    "Người này không phải giáo viên"
            );
        }

        Course course = new Course();

        course.setTitle(req.getTitle());
        course.setDescription(req.getDescription());
        course.setTeacher(teacher);
        course.setPrice(req.getPrice());
        course.setDurationHours(req.getDurationHours());
        // mặc định
        course.setStatus(CourseStatus.DRAFT);
        // lessons rỗng
        course.setLessons(new ArrayList<>());

        Course savedCourse = courseRepository.save(course);

        CourseResponse res = modelMapper.map(savedCourse, CourseResponse.class);

        res.setTeacherUsername(
                savedCourse.getTeacher().getUsername()
        );

        return res;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public CourseResponse updateCourseStatus(
            Integer courseId,
            UpdateStatusCourseRequest req
    ) {

        if (req == null || req.getStatus() == null) {
            throw new IllegalArgumentException(
                    "Không có dữ liệu cập nhật"
            );
        }

        Course course = courseRepository
                .findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy khóa học"
                        ));

        course.setStatus(req.getStatus());

        Course updatedCourse =
                courseRepository.save(course);

        CourseResponse res =
                modelMapper.map(updatedCourse, CourseResponse.class);

        res.setTeacherUsername(
                updatedCourse.getTeacher().getUsername()
        );

        return res;
    }
}
