package com.example.completion_project.repository;

import com.example.completion_project.model.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("""
            select l
            from Lesson l
            where l.course.id = :courseId
            and l.isPublished = true
            order by l.orderIndex asc
            """)
    List<Lesson> findPublishedLessonsByCourse(
            @Param("courseId") Integer courseId
    );
}
