package com.learning.cms.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MaterialResponseDTO {
    private Long id;
    private String title;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private Long courseId;
    private String courseTitle;
    private LocalDateTime uploadDate;
}
