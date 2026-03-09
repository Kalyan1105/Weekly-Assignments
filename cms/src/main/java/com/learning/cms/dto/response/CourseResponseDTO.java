package com.learning.cms.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CourseResponseDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String level;
    private String instructorName;
    private Long instructorId;
    private LocalDateTime createdAt;
}
