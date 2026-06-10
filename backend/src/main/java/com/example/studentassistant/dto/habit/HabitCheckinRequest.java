package com.example.studentassistant.dto.habit;

import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public class HabitCheckinRequest {
    private LocalDate checkinDate;
    @Min(1)
    private Integer checkinCount;
    private String note;

    public LocalDate getCheckinDate() { return checkinDate; }
    public void setCheckinDate(LocalDate checkinDate) { this.checkinDate = checkinDate; }
    public Integer getCheckinCount() { return checkinCount; }
    public void setCheckinCount(Integer checkinCount) { this.checkinCount = checkinCount; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}

