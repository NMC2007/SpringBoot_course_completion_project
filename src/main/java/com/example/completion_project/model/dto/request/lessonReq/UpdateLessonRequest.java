package com.example.completion_project.model.dto.request.lessonReq;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLessonRequest {

    @Size(max = 255, message = "Tiêu đề không được vượt quá 255 ký tự")
    private String title;

    @Size(max = 500, message = "Content URL không được vượt quá 500 ký tự")
    private String contentUrl;

    private String textContent;

    @Min(value = 1, message = "Thứ tự bài học phải lớn hơn 0")
    private Integer orderIndex;
}
