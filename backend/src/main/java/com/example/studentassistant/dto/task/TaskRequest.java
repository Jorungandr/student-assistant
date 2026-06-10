package com.example.studentassistant.dto.task;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class TaskRequest {
    private Long courseId;
    @NotBlank
    private String title;
    private String description;
    private String taskType;
    private String priority;
    private String status;
    private LocalDateTime deadline;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTaskType() { return taskType; }
    public void setTaskType(String taskType) { this.taskType = taskType; }
    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
}

