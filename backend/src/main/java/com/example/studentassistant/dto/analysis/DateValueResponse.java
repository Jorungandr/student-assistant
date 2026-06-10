package com.example.studentassistant.dto.analysis;

import java.time.LocalDate;

public class DateValueResponse {
    private LocalDate date;
    private long value;

    public DateValueResponse() {}

    public DateValueResponse(LocalDate date, long value) {
        this.date = date;
        this.value = value;
    }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public long getValue() { return value; }
    public void setValue(long value) { this.value = value; }
}

