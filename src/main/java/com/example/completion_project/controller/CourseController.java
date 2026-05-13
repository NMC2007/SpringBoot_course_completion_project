package com.example.completion_project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    @GetMapping
    public String test() {
        return "Auth API public OK";
    }

    @PostMapping
    public String createCourse() {
        return "Course created successfully";
    }
}
