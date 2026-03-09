package com.learning.cms.mapper;

import com.learning.cms.dto.response.EnrollmentResponseDTO;
import com.learning.cms.entity.Enrollment;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {

    public EnrollmentResponseDTO toResponseDTO(Enrollment enrollment) {
        if (enrollment == null) return null;

        EnrollmentResponseDTO dto = new EnrollmentResponseDTO();
        dto.setId(enrollment.getId());
        dto.setStatus(enrollment.getStatus());
        dto.setProgressPercentage(enrollment.getProgressPercentage());
        dto.setEnrollmentDate(enrollment.getEnrollmentDate());

        if (enrollment.getCourse() != null) {
            dto.setCourseTitle(enrollment.getCourse().getTitle());
            dto.setCourseId(enrollment.getCourse().getId());
        }
        if (enrollment.getStudent() != null) {
            dto.setStudentName(enrollment.getStudent().getFullName());
            dto.setStudentId(enrollment.getStudent().getId());
        }
        return dto;
    }
}
