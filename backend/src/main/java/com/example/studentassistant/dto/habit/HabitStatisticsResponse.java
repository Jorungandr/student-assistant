package com.example.studentassistant.dto.habit;

public class HabitStatisticsResponse {
    private long habitCount;
    private long todayCheckinCount;
    private int longestStreak;

    public long getHabitCount() { return habitCount; }
    public void setHabitCount(long habitCount) { this.habitCount = habitCount; }
    public long getTodayCheckinCount() { return todayCheckinCount; }
    public void setTodayCheckinCount(long todayCheckinCount) { this.todayCheckinCount = todayCheckinCount; }
    public int getLongestStreak() { return longestStreak; }
    public void setLongestStreak(int longestStreak) { this.longestStreak = longestStreak; }
}

