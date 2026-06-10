package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.goal.GoalRequest;
import com.example.studentassistant.entity.Goal;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.GoalMapper;
import com.example.studentassistant.service.GoalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {
    private final GoalMapper mapper;

    public GoalServiceImpl(GoalMapper mapper) {
        this.mapper = mapper;
    }

    public List<Goal> list(Long userId) {
        return mapper.selectList(new LambdaQueryWrapper<Goal>().eq(Goal::getUserId, userId).orderByAsc(Goal::getEndDate));
    }

    @Transactional
    public Goal create(Long userId, GoalRequest request) {
        Goal goal = new Goal();
        goal.setUserId(userId);
        copy(request, goal);
        mapper.insert(goal);
        return goal;
    }

    @Transactional
    public Goal update(Long userId, Long id, GoalRequest request) {
        Goal goal = requireOwned(userId, id);
        copy(request, goal);
        mapper.updateById(goal);
        return goal;
    }

    @Transactional
    public Goal updateProgress(Long userId, Long id, BigDecimal currentValue) {
        Goal goal = requireOwned(userId, id);
        goal.setCurrentValue(currentValue);
        if (currentValue.compareTo(goal.getTargetValue()) >= 0) {
            goal.setStatus("COMPLETED");
        }
        mapper.updateById(goal);
        return goal;
    }

    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        mapper.deleteById(id);
    }

    private Goal requireOwned(Long userId, Long id) {
        Goal goal = mapper.selectById(id);
        if (goal == null || !userId.equals(goal.getUserId())) throw new BusinessException(404, "目标不存在");
        return goal;
    }

    private void copy(GoalRequest request, Goal goal) {
        goal.setTitle(request.getTitle());
        goal.setDescription(request.getDescription());
        goal.setGoalType(request.getGoalType() == null ? "CUSTOM" : request.getGoalType());
        goal.setTargetValue(request.getTargetValue());
        goal.setCurrentValue(request.getCurrentValue() == null ? BigDecimal.ZERO : request.getCurrentValue());
        goal.setUnit(request.getUnit());
        goal.setStartDate(request.getStartDate());
        goal.setEndDate(request.getEndDate());
        goal.setStatus(request.getStatus() == null ? "ACTIVE" : request.getStatus());
    }
}

