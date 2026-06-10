package com.example.studentassistant.service;

import com.example.studentassistant.dto.habit.HabitCheckinRequest;
import com.example.studentassistant.dto.habit.HabitRequest;
import com.example.studentassistant.dto.habit.HabitStatisticsResponse;
import com.example.studentassistant.entity.Habit;
import com.example.studentassistant.entity.HabitCheckin;

import java.util.List;

public interface HabitService {
    List<Habit> list(Long userId);
    Habit create(Long userId, HabitRequest request);
    Habit update(Long userId, Long id, HabitRequest request);
    void delete(Long userId, Long id);
    HabitCheckin checkin(Long userId, Long habitId, HabitCheckinRequest request);
    List<HabitCheckin> checkins(Long userId, Long habitId);
    HabitStatisticsResponse statistics(Long userId);
    int continuousDays(Long userId, Long habitId);
}

