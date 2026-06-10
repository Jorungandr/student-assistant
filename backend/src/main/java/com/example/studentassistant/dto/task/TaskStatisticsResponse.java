package com.example.studentassistant.dto.task;

public class TaskStatisticsResponse {
    private long total;
    private long todo;
    private long inProgress;
    private long completed;
    private long overdue;

    public long getTotal() { return total; }
    public void setTotal(long total) { this.total = total; }
    public long getTodo() { return todo; }
    public void setTodo(long todo) { this.todo = todo; }
    public long getInProgress() { return inProgress; }
    public void setInProgress(long inProgress) { this.inProgress = inProgress; }
    public long getCompleted() { return completed; }
    public void setCompleted(long completed) { this.completed = completed; }
    public long getOverdue() { return overdue; }
    public void setOverdue(long overdue) { this.overdue = overdue; }
}

