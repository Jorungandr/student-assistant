package com.example.studentassistant.dto.analysis;

import java.math.BigDecimal;

public class TaskCompletionResponse {
    private long total;
    private long completed;
    private BigDecimal completionRate;

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public long getCompleted() { return completed; }
    public void setCompleted(long completed) { this.completed = completed; }
    public BigDecimal getCompletionRate() { return completionRate; }
    public void setCompletionRate(BigDecimal completionRate) { this.completionRate = completionRate; }
}

