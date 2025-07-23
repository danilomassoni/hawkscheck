package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.TaskStatusEnum;
import jakarta.validation.constraints.NotNull;

public class TaskStatusUpdateDTO {

    @NotNull
    private TaskStatusEnum status;

    // Getter e Setter
    public TaskStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TaskStatusEnum status) {
        this.status = status;
    }
}
