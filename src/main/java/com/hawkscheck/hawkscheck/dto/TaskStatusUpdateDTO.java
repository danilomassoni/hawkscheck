package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.TaskStatusEnum;
import jakarta.validation.constraints.NotNull;

public class TaskStatusUpdateDTO {

    @NotNull(message = "Status não pode ser nulo")
    private TaskStatusEnum status;

    // Getters e Setters
    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }
}
