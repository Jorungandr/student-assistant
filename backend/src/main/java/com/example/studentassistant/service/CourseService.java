package com.example.studentassistant.service;

import com.example.studentassistant.dto.course.CourseRequest;
import com.example.studentassistant.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> list(Long userId);
    List<Course> today(Long userId);
    Course create(Long userId, CourseRequest request);
    Course update(Long userId, Long id, CourseRequest request);
    void delete(Long userId, Long id);
}

