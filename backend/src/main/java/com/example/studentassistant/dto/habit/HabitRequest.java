package com.example.studentassistant.dto.habit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class HabitRequest {
    @NotBlank
    private String habitName;
    private String description;
    private String frequency;
    @Min(1)
    private Integer targetCount;
    private String status;

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
}

