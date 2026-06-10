package com.example.studentassistant.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("health_record")
public class HealthRecord {
    private Long id;
    @TableField("user_id")
    private Long userId;
    @TableField("record_date")
    private LocalDate recordDate;
    private String weather;
    @TableField("sleep_hours")
    private BigDecimal sleepHours;
    @TableField("water_ml")
    private Integer waterMl;
    @TableField("exercise_minutes")
    private Integer exerciseMinutes;
    @TableField("mood_score")
    private Integer moodScore;
    @TableField("stress_score")
    private Integer stressScore;
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
    public LocalDate getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDate recordDate) { this.recordDate = recordDate; }
    public String getWeather() { return weather; }
    public void setWeather(String weather) { this.weather = weather; }
    public BigDecimal getSleepHours() { return sleepHours; }
    public void setSleepHours(BigDecimal sleepHours) { this.sleepHours = sleepHours; }
    public Integer getWaterMl() { return waterMl; }
    public void setWaterMl(Integer waterMl) { this.waterMl = waterMl; }
    public Integer getExerciseMinutes() { return exerciseMinutes; }
    public void setExerciseMinutes(Integer exerciseMinutes) { this.exerciseMinutes = exerciseMinutes; }
    public Integer getMoodScore() { return moodScore; }
    public void setMoodScore(Integer moodScore) { this.moodScore = moodScore; }
    public Integer getStressScore() { return stressScore; }
    public void setStressScore(Integer stressScore) { this.stressScore = stressScore; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}

