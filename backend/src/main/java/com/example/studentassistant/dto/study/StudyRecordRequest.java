package com.example.studentassistant.dto.study;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class StudyRecordRequest {
    private Long courseId;
    @NotNull
    private LocalDate studyDate;
    @NotNull
    @Min(1)
    private Integer durationMinutes;
    private String content;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public LocalDate getStudyDate() { return studyDate; }
    public void setStudyDate(LocalDate studyDate) { this.studyDate = studyDate; }
    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

