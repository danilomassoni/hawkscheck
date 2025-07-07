package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.TaskPriorityEnum;
import com.hawkscheck.hawkscheck.model.TaskStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class TaskResponseDTO {
    private Long id;
    private String title;
    private String topic;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskPriorityEnum priority;
    private TaskStatusEnum status;
    private Long mentorId;
    private String mentorName;
    private Long teamId;
    private String teamName;
    private Set<Long> studentIds;
}
