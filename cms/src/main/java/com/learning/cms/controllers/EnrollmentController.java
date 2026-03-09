package com.learning.cms.controllers;

import com.learning.cms.dto.request.EnrollmentRequestDTO;
import com.learning.cms.dto.response.ApiResponse;
import com.learning.cms.dto.response.EnrollmentResponseDTO;
import com.learning.cms.entity.EnrollmentStatus;
import com.learning.cms.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
@Tag(name = "Enrollments", description = "Student enrollment management")
@SecurityRequirement(name = "bearerAuth")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    @Operation(summary = "Enroll a student in a course")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> enroll(
            @Valid @RequestBody EnrollmentRequestDTO request) {

        EnrollmentResponseDTO enrollment = enrollmentService.enrollStudent(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Enrollment successful", enrollment));
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all enrollments for a student")
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getByStudent(
            @PathVariable Long studentId) {

        List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);
        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched successfully", enrollments));
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get all enrollments for a course")
    public ResponseEntity<ApiResponse<List<EnrollmentResponseDTO>>> getByCourse(
            @PathVariable Long courseId) {

        List<EnrollmentResponseDTO> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success("Enrollments fetched successfully", enrollments));
    }

    @PatchMapping("/{id}/progress")
    @Operation(summary = "Update progress percentage for an enrollment")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> updateProgress(
            @PathVariable Long id,
            @RequestParam Double progressPercentage) {

        EnrollmentResponseDTO updated = enrollmentService.updateProgress(id, progressPercentage);
        return ResponseEntity.ok(ApiResponse.success("Progress updated", updated));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update enrollment status (ACTIVE/COMPLETED/CANCELLED)")
    public ResponseEntity<ApiResponse<EnrollmentResponseDTO>> updateStatus(
            @PathVariable Long id,
            @RequestParam EnrollmentStatus status) {

        EnrollmentResponseDTO updated = enrollmentService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success("Status updated", updated));
    }
}
