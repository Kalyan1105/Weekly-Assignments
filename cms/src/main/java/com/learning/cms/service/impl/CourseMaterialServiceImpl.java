package com.learning.cms.service.impl;

import com.learning.cms.dto.request.MaterialUploadDTO;
import com.learning.cms.dto.response.MaterialResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.CourseMaterial;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.mapper.MaterialMapper;
import com.learning.cms.repository.CourseMaterialRepository;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.service.CourseMaterialService;
import com.learning.cms.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseMaterialServiceImpl implements CourseMaterialService {

    private final CourseMaterialRepository materialRepository;
    private final CourseRepository courseRepository;
    private final MaterialMapper materialMapper;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public MaterialResponseDTO uploadMaterial(MaterialUploadDTO dto, MultipartFile file) {
        // Validate course exists
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", dto.getCourseId()));

        // Store the file and get the saved file name
        String savedFileName = fileStorageUtil.storeFile(file);
        String fileUrl = "/api/materials/download/" + savedFileName;

        CourseMaterial material = CourseMaterial.builder()
                .title(dto.getTitle())
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileUrl(fileUrl)
                .course(course)
                .build();

        CourseMaterial saved = materialRepository.save(material);
        return materialMapper.toResponseDTO(saved);
    }

    @Override
    public Resource downloadMaterial(Long materialId) {
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", materialId));

        // Extract the stored file name from the URL
        String fileUrl = material.getFileUrl();
        String storedFileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        return fileStorageUtil.loadFileAsResource(storedFileName);
    }

    @Override
    public String getMaterialFileName(Long materialId) {
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", materialId));
        return material.getFileName();
    }

    @Override
    public List<MaterialResponseDTO> getMaterialsByCourse(Long courseId) {
        // Validate course exists
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));

        return materialRepository.findByCourseId(courseId)
                .stream()
                .map(materialMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteMaterial(Long materialId) {
        CourseMaterial material = materialRepository.findById(materialId)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", materialId));

        // Delete the physical file
        String fileUrl = material.getFileUrl();
        String storedFileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        fileStorageUtil.deleteFile(storedFileName);

        materialRepository.delete(material);
    }
}
