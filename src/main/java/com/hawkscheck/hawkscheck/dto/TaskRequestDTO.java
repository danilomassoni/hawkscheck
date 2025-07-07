package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.TaskPriorityEnum;
import com.hawkscheck.hawkscheck.model.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class TaskRequestDTO {
    private String title;
    private String topic;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private TaskPriorityEnum priority;
    private TaskStatusEnum status;
    private Long teamId; // 👈 necessário para associar à equipe
    private Set<Long> studentIds; // IDs dos alunos associados
}
