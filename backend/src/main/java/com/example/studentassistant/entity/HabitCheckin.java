package com.example.studentassistant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("habit_checkin")
public class HabitCheckin {
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("habit_id")
    private Long habitId;
    @TableField("checkin_date")
    private LocalDate checkinDate;
    @TableField("checkin_count")
    private Integer checkinCount;
    private String note;
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
    public Long getHabitId() { return habitId; }
    public void setHabitId(Long habitId) { this.habitId = habitId; }
    public LocalDate getCheckinDate() { return checkinDate; }
    public void setCheckinDate(LocalDate checkinDate) { this.checkinDate = checkinDate; }
    public Integer getCheckinCount() { return checkinCount; }
    public void setCheckinCount(Integer checkinCount) { this.checkinCount = checkinCount; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

