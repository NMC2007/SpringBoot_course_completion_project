package com.example.completion_project.repository;

import com.example.completion_project.model.entity.Enrollment;
import com.example.completion_project.model.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
    boolean existsByStudentIdAndCourseId(Integer studentId, Integer courseId);
    List<Enrollment> findByStudentId(Integer studentId);
    Optional<Enrollment> findByIdAndStudentId(Integer enrollmentId, Integer studentId);

    @Query("""
        select
            (
                count(e) > 0
            )
        from Enrollment e
        where e.course.id = :courseId
        """)
    boolean existsEnrollmentByCourse(
            @Param("courseId") Integer courseId
    );
}
