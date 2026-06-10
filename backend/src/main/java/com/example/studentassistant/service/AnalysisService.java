package com.example.studentassistant.service;

import com.example.studentassistant.dto.analysis.DateValueResponse;
import com.example.studentassistant.dto.analysis.NameValueResponse;
import com.example.studentassistant.dto.analysis.TaskCompletionResponse;
import com.example.studentassistant.entity.Task;

import java.util.List;

public interface AnalysisService {
    List<DateValueResponse> studyDuration(Long userId);
    TaskCompletionResponse taskCompletion(Long userId);
    List<NameValueResponse> courseRatio(Long userId);
    List<Task> priorityTasks(Long userId);
}

