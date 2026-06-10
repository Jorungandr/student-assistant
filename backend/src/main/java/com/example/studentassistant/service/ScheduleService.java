package com.example.studentassistant.service;

import com.example.studentassistant.dto.schedule.ScheduleRequest;
import com.example.studentassistant.entity.Schedule;

import java.util.List;

public interface ScheduleService {
    List<Schedule> list(Long userId);
    Schedule create(Long userId, ScheduleRequest request);
    Schedule update(Long userId, Long id, ScheduleRequest request);
    void delete(Long userId, Long id);
}

