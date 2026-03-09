package com.learning.cms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MaterialUploadDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Course ID is required")
    private Long courseId;
}
