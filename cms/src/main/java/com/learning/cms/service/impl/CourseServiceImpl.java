package com.learning.cms.service.impl;

import com.learning.cms.dto.request.CourseRequestDTO;
import com.learning.cms.dto.response.CourseResponseDTO;
import com.learning.cms.entity.Course;
import com.learning.cms.entity.Role;
import com.learning.cms.entity.User;
import com.learning.cms.exception.InvalidRequestException;
import com.learning.cms.exception.ResourceNotFoundException;
import com.learning.cms.mapper.CourseMapper;
import com.learning.cms.repository.CourseRepository;
import com.learning.cms.repository.UserRepository;
import com.learning.cms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO createCourse(CourseRequestDTO request) {
        // Validate that the instructor exists and has the INSTRUCTOR role
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getInstructorId()));

        if (instructor.getRole() != Role.INSTRUCTOR && instructor.getRole() != Role.ADMIN) {
            throw new InvalidRequestException("User is not an instructor");
        }

        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .duration(request.getDuration())
                .level(request.getLevel())
                .instructor(instructor)
                .build();

        Course saved = courseRepository.save(course);
        return courseMapper.toResponseDTO(saved);
    }

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public CourseResponseDTO updateCourse(Long id, CourseRequestDTO request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));

        course.setTitle(request.getTitle());
        course.setDescription(request.getDescription());
        course.setPrice(request.getPrice());
        course.setDuration(request.getDuration());
        course.setLevel(request.getLevel());

        Course updated = courseRepository.save(course);
        return courseMapper.toResponseDTO(updated);
    }

    @Override
    @CacheEvict(value = "courses", allEntries = true)
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        courseRepository.delete(course);
    }

    @Override
    @Cacheable(value = "courses", key = "#id")
    public CourseResponseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "id", id));
        return courseMapper.toResponseDTO(course);
    }

    @Override
    @Cacheable(value = "courses", key = "'all_' + #pageable.pageNumber + '_' + #pageable.pageSize")
    public Page<CourseResponseDTO> getAllCourses(Pageable pageable) {
        return courseRepository.findAll(pageable)
                .map(courseMapper::toResponseDTO);
    }

    @Override
    public Page<CourseResponseDTO> searchCourses(String keyword, Pageable pageable) {
        return courseRepository.searchCourses(keyword, pageable)
                .map(courseMapper::toResponseDTO);
    }

    @Override
    public Page<CourseResponseDTO> getCoursesByInstructor(Long instructorId, Pageable pageable) {
        // Validate instructor exists
        userRepository.findById(instructorId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", instructorId));
        return courseRepository.findByInstructorId(instructorId, pageable)
                .map(courseMapper::toResponseDTO);
    }
}
