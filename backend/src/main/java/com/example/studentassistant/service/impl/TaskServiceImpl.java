package com.example.studentassistant.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.studentassistant.dto.task.TaskRequest;
import com.example.studentassistant.dto.task.TaskStatisticsResponse;
import com.example.studentassistant.entity.Course;
import com.example.studentassistant.entity.Task;
import com.example.studentassistant.exception.BusinessException;
import com.example.studentassistant.mapper.CourseMapper;
import com.example.studentassistant.mapper.TaskMapper;
import com.example.studentassistant.service.TaskService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskMapper taskMapper;
    private final CourseMapper courseMapper;

    public TaskServiceImpl(TaskMapper taskMapper, CourseMapper courseMapper) {
        this.taskMapper = taskMapper;
        this.courseMapper = courseMapper;
    }

    @Override
    public List<Task> list(Long userId) {
        return taskMapper.selectList(new LambdaQueryWrapper<Task>()
                .eq(Task::getUserId, userId)
                .orderByAsc(Task::getDeadline));
    }

    @Override
    public List<Task> upcoming(Long userId) {
        return taskMapper.selectList(new LambdaQueryWrapper<Task>()
                .eq(Task::getUserId, userId)
                .ne(Task::getStatus, "COMPLETED")
                .ge(Task::getDeadline, LocalDateTime.now())
                .orderByAsc(Task::getDeadline)
                .last("LIMIT 10"));
    }

    @Override
    @Transactional
    public Task create(Long userId, TaskRequest request) {
        validateCourse(userId, request.getCourseId());
        Task task = new Task();
        task.setUserId(userId);
        copy(request, task);
        taskMapper.insert(task);
        return task;
    }

    @Override
    @Transactional
    public Task update(Long userId, Long id, TaskRequest request) {
        validateCourse(userId, request.getCourseId());
        Task task = requireOwned(userId, id);
        copy(request, task);
        taskMapper.updateById(task);
        return task;
    }

    @Override
    @Transactional
    public Task updateStatus(Long userId, Long id, String status) {
        Task task = requireOwned(userId, id);
        task.setStatus(status);
        task.setCompletedAt("COMPLETED".equals(status) ? LocalDateTime.now() : null);
        taskMapper.updateById(task);
        return task;
    }

    @Override
    public TaskStatisticsResponse statistics(Long userId) {
        List<Task> tasks = list(userId);
        LocalDateTime now = LocalDateTime.now();
        TaskStatisticsResponse response = new TaskStatisticsResponse();
        response.setTotal(tasks.size());
        response.setTodo(tasks.stream().filter(task -> "TODO".equals(task.getStatus())).count());
        response.setInProgress(tasks.stream().filter(task -> "IN_PROGRESS".equals(task.getStatus())).count());
        response.setCompleted(tasks.stream().filter(task -> "COMPLETED".equals(task.getStatus())).count());
        response.setOverdue(tasks.stream()
                .filter(task -> task.getDeadline() != null)
                .filter(task -> task.getDeadline().isBefore(now))
                .filter(task -> !"COMPLETED".equals(task.getStatus()))
                .count());
        return response;
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        requireOwned(userId, id);
        taskMapper.deleteById(id);
    }

    private Task requireOwned(Long userId, Long id) {
        Task task = taskMapper.selectById(id);
        if (task == null || !userId.equals(task.getUserId())) {
            throw new BusinessException(404, "任务不存在");
        }
        return task;
    }

    private void validateCourse(Long userId, Long courseId) {
        if (courseId == null) {
            return;
        }
        Course course = courseMapper.selectById(courseId);
        if (course == null || !userId.equals(course.getUserId())) {
            throw new BusinessException("关联课程不存在");
        }
    }

    private void copy(TaskRequest request, Task task) {
        task.setCourseId(request.getCourseId());
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setTaskType(request.getTaskType() == null ? "TODO" : request.getTaskType());
        task.setPriority(request.getPriority() == null ? "MEDIUM" : request.getPriority());
        task.setStatus(request.getStatus() == null ? "TODO" : request.getStatus());
        task.setDeadline(request.getDeadline());
        task.setCompletedAt("COMPLETED".equals(task.getStatus()) ? LocalDateTime.now() : null);
    }
}

