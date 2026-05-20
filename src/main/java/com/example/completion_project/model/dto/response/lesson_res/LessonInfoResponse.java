package com.example.completion_project.model.dto.response.lesson_res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonInfoResponse {

    private Integer id;
    private String title;
    private String contentUrl;
    private String textContent;
    private Integer orderIndex;
    private Boolean isPublished;
}
