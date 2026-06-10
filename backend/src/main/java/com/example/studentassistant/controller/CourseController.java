package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.course.CourseRequest;
import com.example.studentassistant.entity.Course;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ApiResponse<List<Course>> list() {
        return ApiResponse.success(courseService.list(SecurityUtils.currentUserId()));
    }

    @GetMapping("/today")
    public ApiResponse<List<Course>> today() {
        return ApiResponse.success(courseService.today(SecurityUtils.currentUserId()));
    }

    @PostMapping
    public ApiResponse<Course> create(@Valid @RequestBody CourseRequest request) {
        return ApiResponse.success(courseService.create(SecurityUtils.currentUserId(), request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Course> update(@PathVariable Long id, @Valid @RequestBody CourseRequest request) {
        return ApiResponse.success(courseService.update(SecurityUtils.currentUserId(), id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courseService.delete(SecurityUtils.currentUserId(), id);
        return ApiResponse.success();
    }
}

