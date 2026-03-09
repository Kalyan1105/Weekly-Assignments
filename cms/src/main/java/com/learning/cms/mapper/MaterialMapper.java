package com.learning.cms.mapper;

import com.learning.cms.dto.response.MaterialResponseDTO;
import com.learning.cms.entity.CourseMaterial;
import org.springframework.stereotype.Component;

@Component
public class MaterialMapper {

    public MaterialResponseDTO toResponseDTO(CourseMaterial material) {
        if (material == null) return null;

        MaterialResponseDTO dto = new MaterialResponseDTO();
        dto.setId(material.getId());
        dto.setTitle(material.getTitle());
        dto.setFileName(material.getFileName());
        dto.setFileType(material.getFileType());
        dto.setFileUrl(material.getFileUrl());
        dto.setUploadDate(material.getUploadDate());

        if (material.getCourse() != null) {
            dto.setCourseId(material.getCourse().getId());
            dto.setCourseTitle(material.getCourse().getTitle());
        }
        return dto;
    }
}
