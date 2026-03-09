package com.learning.cms.service;

import com.learning.cms.dto.request.EnrollmentRequestDTO;
import com.learning.cms.dto.response.EnrollmentResponseDTO;
import com.learning.cms.entity.EnrollmentStatus;

import java.util.List;

public interface EnrollmentService {

    EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request);

    List<EnrollmentResponseDTO> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentResponseDTO> getEnrollmentsByCourse(Long courseId);

    EnrollmentResponseDTO updateProgress(Long enrollmentId, Double progressPercentage);

    EnrollmentResponseDTO updateStatus(Long enrollmentId, EnrollmentStatus status);
}
