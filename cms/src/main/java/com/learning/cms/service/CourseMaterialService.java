package com.learning.cms.service;

import com.learning.cms.dto.request.MaterialUploadDTO;
import com.learning.cms.dto.response.MaterialResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseMaterialService {

    MaterialResponseDTO uploadMaterial(MaterialUploadDTO dto, MultipartFile file);

    Resource downloadMaterial(Long materialId);

    String getMaterialFileName(Long materialId);

    List<MaterialResponseDTO> getMaterialsByCourse(Long courseId);

    void deleteMaterial(Long materialId);
}
