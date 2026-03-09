package com.learning.cms.controllers;

import com.learning.cms.dto.request.MaterialUploadDTO;
import com.learning.cms.dto.response.ApiResponse;
import com.learning.cms.dto.response.MaterialResponseDTO;
import com.learning.cms.service.CourseMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/materials")
@RequiredArgsConstructor
@Tag(name = "Course Materials", description = "Upload and download course materials")
@SecurityRequirement(name = "bearerAuth")
public class CourseMaterialController {

    private final CourseMaterialService materialService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a material file to a course")
    public ResponseEntity<ApiResponse<MaterialResponseDTO>> uploadMaterial(
            @Valid @ModelAttribute MaterialUploadDTO dto,
            @RequestParam("file") MultipartFile file) {

        MaterialResponseDTO material = materialService.uploadMaterial(dto, file);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("Material uploaded successfully", material));
    }

    @GetMapping("/{id}/download")
    @Operation(summary = "Download a course material file")
    public ResponseEntity<Resource> downloadMaterial(@PathVariable Long id) {
        Resource resource = materialService.downloadMaterial(id);
        String fileName = materialService.getMaterialFileName(id);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @GetMapping("/course/{courseId}")
    @Operation(summary = "Get all materials for a course")
    public ResponseEntity<ApiResponse<List<MaterialResponseDTO>>> getMaterialsByCourse(
            @PathVariable Long courseId) {

        List<MaterialResponseDTO> materials = materialService.getMaterialsByCourse(courseId);
        return ResponseEntity.ok(ApiResponse.success("Materials fetched successfully", materials));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a course material")
    public ResponseEntity<ApiResponse<Void>> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        return ResponseEntity.ok(ApiResponse.success("Material deleted successfully", null));
    }
}
