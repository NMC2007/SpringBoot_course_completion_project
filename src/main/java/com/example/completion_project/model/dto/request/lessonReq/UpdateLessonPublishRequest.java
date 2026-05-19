package com.example.completion_project.model.dto.request.lessonReq;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateLessonPublishRequest {

    @NotNull(message = "Trạng thái publish không được để trống")
    private Boolean isPublished;
}
