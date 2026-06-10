package com.example.studentassistant.dto.health;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class HealthRecordRequest {
    @NotNull
    private LocalDate recordDate;
    private String weather;
    private BigDecimal sleepHours;
    private Integer waterMl;
    private Integer exerciseMinutes;
    @Min(1)
    @Max(10)
    private Integer moodScore;
    @Min(1)
    @Max(10)
    private Integer stressScore;
    private String note;

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
}

