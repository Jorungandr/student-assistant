package com.example.studentassistant.controller;

import com.example.studentassistant.common.ApiResponse;
import com.example.studentassistant.dto.study.StudyRecordRequest;
import com.example.studentassistant.entity.StudyRecord;
import com.example.studentassistant.security.SecurityUtils;
import com.example.studentassistant.service.StudyRecordService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study-records")
public class StudyRecordController {
    private final StudyRecordService service;

    public StudyRecordController(StudyRecordService service) { this.service = service; }

    @GetMapping
    public ApiResponse<List<StudyRecord>> list() { return ApiResponse.success(service.list(SecurityUtils.currentUserId())); }

    @PostMapping
    public ApiResponse<StudyRecord> create(@Valid @RequestBody StudyRecordRequest request) { return ApiResponse.success(service.create(SecurityUtils.currentUserId(), request)); }

    @PutMapping("/{id}")
    public ApiResponse<StudyRecord> update(@PathVariable Long id, @Valid @RequestBody StudyRecordRequest request) { return ApiResponse.success(service.update(SecurityUtils.currentUserId(), id, request)); }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) { service.delete(SecurityUtils.currentUserId(), id); return ApiResponse.success(); }
}

