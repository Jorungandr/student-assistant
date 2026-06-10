package com.example.studentassistant.service;

import com.example.studentassistant.dto.analysis.DateValueResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.study.StudyRecordRequest;
import com.example.studentassistant.entity.StudyRecord;

import java.util.List;

public interface StudyRecordService {
    List<StudyRecord> list(Long userId);
    StudyRecord create(Long userId, StudyRecordRequest request);
    StudyRecord update(Long userId, Long id, StudyRecordRequest request);
    void delete(Long userId, Long id);
    List<DateValueResponse> durationTrend(Long userId);
    List<NameValueResponse> courseRatio(Long userId);
}

