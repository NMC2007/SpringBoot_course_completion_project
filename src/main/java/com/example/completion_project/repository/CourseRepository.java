package com.example.completion_project.repository;

import com.example.completion_project.model.Enum.CourseStatus;
import com.example.completion_project.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("""
            select c
            from Course c
            where (:status is null or c.status = :status)
            order by c.createdAt desc
            """)
    List<Course> findAllCourses(
            @Param("status") CourseStatus status
    );
}
