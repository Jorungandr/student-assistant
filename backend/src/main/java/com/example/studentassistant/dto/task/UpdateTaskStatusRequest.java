package com.example.studentassistant.dto.task;

import jakarta.validation.constraints.NotBlank;

public class UpdateTaskStatusRequest {
    @NotBlank
    private String status;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

