package com.learning.cms.mapper;

import com.learning.cms.dto.response.CourseResponseDTO;
import com.learning.cms.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {

    public CourseResponseDTO toResponseDTO(Course course) {
        if (course == null) return null;

        CourseResponseDTO dto = new CourseResponseDTO();
        dto.setId(course.getId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setPrice(course.getPrice());
        dto.setDuration(course.getDuration());
        dto.setLevel(course.getLevel());
        dto.setCreatedAt(course.getCreatedAt());

        if (course.getInstructor() != null) {
            dto.setInstructorName(course.getInstructor().getFullName());
            dto.setInstructorId(course.getInstructor().getId());
        }
        return dto;
    }
}
