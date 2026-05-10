package com.example.completion_project.repository;

import com.example.completion_project.model.Enum.Role;
import com.example.completion_project.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    sử dụng cho auth
    Optional<User> findUserByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

//    curd
    @Query("""
                select u
                from User u
                where (:role is null or u.role = :role)
                  and (:status is null or u.isActive = :status)
                order by u.id asc
                """)
    List<User> findUsersWithFilter(
            @Param("role") Role role,
            @Param("status") Boolean status
    );
}
