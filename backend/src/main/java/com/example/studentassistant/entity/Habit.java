package com.example.studentassistant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDateTime;

@TableName("habit")
public class Habit {
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("habit_name")
    private String habitName;
    private String description;
    private String frequency;
    @TableField("target_count")
    private Integer targetCount;
    private String status;
    @TableLogic
    private Integer deleted;
    @TableField("created_at")
    private LocalDateTime createdAt;
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getHabitName() { return habitName; }
    public void setHabitName(String habitName) { this.habitName = habitName; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public Integer getTargetCount() { return targetCount; }
    public void setTargetCount(Integer targetCount) { this.targetCount = targetCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

