package com.example.completion_project.repository;

import com.example.completion_project.model.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Integer> {
    @Query("""
            select lp
            from LessonProgress lp
            where lp.enrollment.id = :enrollmentId
            and lp.lesson.id = :lessonId
            """)
    Optional<LessonProgress> findLessonProgress(
            @Param("enrollmentId") Integer enrollmentId,
            @Param("lessonId") Integer lessonId
    );

    @Query("""
            select count(lp)
            from LessonProgress lp
            where lp.enrollment.id = :enrollmentId
            and lp.isCompleted = true
            """)
    long countCompletedLessons(
            @Param("enrollmentId") Integer enrollmentId
    );
}
