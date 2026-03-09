package com.learning.cms.dto.response;

import com.learning.cms.entity.EnrollmentStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnrollmentResponseDTO {
    private Long id;
    private String courseTitle;
    private Long courseId;
    private String studentName;
    private Long studentId;
    private EnrollmentStatus status;
    private Double progressPercentage;
    private LocalDateTime enrollmentDate;
}
