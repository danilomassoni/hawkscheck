package com.hawkscheck.hawkscheck.dto;

import java.time.LocalDate;
import java.util.List;

import com.hawkscheck.hawkscheck.model.TaskPriorityEnum;
import com.hawkscheck.hawkscheck.model.TaskStatusEnum;

import lombok.Data;

@Data
public class TaskRequestDTO {
    private String title;
    private String topic;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskPriorityEnum priority;
    private TaskStatusEnum status;

    private Long mentorId;
    private List<Long> studentIds;
}
