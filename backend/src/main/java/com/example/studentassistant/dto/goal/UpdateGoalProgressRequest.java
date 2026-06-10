package com.example.studentassistant.dto.goal;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class UpdateGoalProgressRequest {
    @NotNull
    private BigDecimal currentValue;

    public BigDecimal getCurrentValue() { return currentValue; }
    public void setCurrentValue(BigDecimal currentValue) { this.currentValue = currentValue; }
}

