package com.learning.cms.service.impl;

import com.learning.cms.dto.request.EnrollmentRequestDTO;
import com.learning.cms.dto.response.EnrollmentResponseDTO;
import com.learning.cms.entity.*;
import com.learning.cms.exception.InvalidRequestException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.mapper.EnrollmentMapper;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.repository.EnrollmentRepository;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponseDTO enrollStudent(EnrollmentRequestDTO request) {
        // Validate student exists and has STUDENT role
        User student = userRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getStudentId()));

        if (student.getRole() != Role.STUDENT) {
            throw new InvalidRequestException("User is not a student");
        }

        // Validate course exists
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", request.getCourseId()));

        // Check if student is already enrolled
        if (enrollmentRepository.existsByStudentIdAndCourseId(request.getStudentId(), request.getCourseId())) {
            throw new InvalidRequestException("Student is already enrolled in this course");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .status(EnrollmentStatus.ACTIVE)
                .progressPercentage(0.0)
                .build();

        Enrollment saved = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDTO(saved);
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByStudent(Long studentId) {
        // Validate student exists
        userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", studentId));

        return enrollmentRepository.findByStudentId(studentId)
                .stream()
                .map(enrollmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDTO> getEnrollmentsByCourse(Long courseId) {
        // Validate course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(enrollmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDTO updateProgress(Long enrollmentId, Double progressPercentage) {
        if (progressPercentage < 0 || progressPercentage > 100) {
            throw new InvalidRequestException("Progress percentage must be between 0 and 100");
        }

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", enrollmentId));

        enrollment.setProgressPercentage(progressPercentage);

        // Auto-complete if 100%
        if (progressPercentage == 100.0) {
            enrollment.setStatus(EnrollmentStatus.COMPLETED);
        }

        Enrollment updated = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDTO(updated);
    }

    @Override
    public EnrollmentResponseDTO updateStatus(Long enrollmentId, EnrollmentStatus status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment", "id", enrollmentId));

        enrollment.setStatus(status);
        Enrollment updated = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponseDTO(updated);
    }
}
