package com.example.completion_project.service.impl;

import com.example.completion_project.exception.AccessDeniedExceptionCustom;
import com.example.completion_project.exception.DuplicateResourceException;
import com.example.completion_project.exception.ResourceNotFoundException;
import com.example.completion_project.model.dto.request.enrollment_req.EnrollmentCreateRequest;
import com.example.completion_project.model.dto.response.enrollment_res.EnrollmentResponse;
import com.example.completion_project.model.entity.Course;
import com.example.completion_project.model.entity.Enrollment;
import com.example.completion_project.model.entity.User;
import com.example.completion_project.model.enums.CourseStatus;
import com.example.completion_project.model.enums.EnrollmentStatus;
import com.example.completion_project.repository.CourseRepository;
import com.example.completion_project.repository.EnrollmentRepository;
import com.example.completion_project.repository.UserRepository;
import com.example.completion_project.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    private final ModelMapper modelMapper;

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentCreateRequest req) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User student = userRepository.findUserByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy học sinh"
                        ));


        Course course = courseRepository.findById(req.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy khóa học"
                        ));

        if (course.getStatus() != CourseStatus.PUBLISHED) {
            throw new AccessDeniedExceptionCustom(
                    "Khoá học chưa công bố"
            );
        }

        boolean isEnrolled = enrollmentRepository
                .existsByStudentIdAndCourseId(student.getId(), course.getId());

        if  (isEnrolled) {
            throw new DuplicateResourceException(
                    "Khoá học này đã được đăng ký"
            );
        }

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.ENROLLED);
        enrollment.setProgressPercent(BigDecimal.ZERO);

        LocalDateTime completionDate =
                LocalDateTime.now().plusHours(course.getDurationHours());
        enrollment.setCompletionDate(completionDate);

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);

        EnrollmentResponse res =
                modelMapper.map(savedEnrollment, EnrollmentResponse.class);

        res.setStudentId(student.getId());
        res.setStudentUsername(student.getUsername());
        res.setCourseId(course.getId());
        res.setCourseTitle(course.getTitle());

        return res;
    }

    @Override
    public List<EnrollmentResponse> getMyEnrollments() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User student = userRepository
                .findUserByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy học sinh"
                        ));

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(
                        student.getId()
                );

        return enrollments.stream()
                .map(enrollment -> {

                    EnrollmentResponse res =
                            modelMapper.map(
                                    enrollment,
                                    EnrollmentResponse.class
                            );

                    res.setStudentId(enrollment.getStudent().getId());
                    res.setStudentUsername(enrollment.getStudent().getUsername());
                    res.setCourseId(enrollment.getCourse().getId());
                    res.setCourseTitle(enrollment.getCourse().getTitle());

                    return res;
                })
                .toList();
    }

    @Override
    public EnrollmentResponse getEnrollmentDetail(Integer enrollmentId) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        User student = userRepository
                .findUserByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Không tìm thấy học sinh"
                        ));

        Enrollment enrollment =
                enrollmentRepository.findByIdAndStudentId(
                                enrollmentId,
                                student.getId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Sinh viên chưa đăng ký khóa học hoặc khoá học không tồn tại"
                                ));

        EnrollmentResponse res =
                modelMapper.map(enrollment, EnrollmentResponse.class);

        res.setStudentId(enrollment.getStudent().getId());
        res.setStudentUsername(enrollment.getStudent().getUsername());
        res.setCourseId(enrollment.getCourse().getId());
        res.setCourseTitle(enrollment.getCourse().getTitle());

        return res;
    }
}
