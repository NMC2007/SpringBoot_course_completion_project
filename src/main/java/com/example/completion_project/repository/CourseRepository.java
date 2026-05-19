package com.example.completion_project.repository;

import com.example.completion_project.model.enums.CourseStatus;
import com.example.completion_project.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("""
        select c
        from Course c
        where
            (:status is null or c.status = :status)
        and
            (
                :keyword = ''
                or lower(c.title)
                    like lower(concat('%', :keyword, '%'))
                or lower(c.description)
                    like lower(concat('%', :keyword, '%'))
            )
        and
            (:teacherId is null or c.teacher.id = :teacherId)
        order by c.createdAt desc
        """)
    List<Course> findCourses(
            @Param("status") CourseStatus status,
            @Param("keyword") String keyword,
            @Param("teacherId") Integer teacherId
    );

    @Query("""
        select distinct c
        from Course c
        left join fetch c.lessons l
        where c.id = :courseId
        order by l.orderIndex asc
        """)
    Optional<Course> findCourseDetail(
            @Param("courseId") Integer courseId
    );
}
