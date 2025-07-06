package com.hawkscheck.hawkscheck.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

import com.hawkscheck.hawkscheck.model.TaskPriorityEnum;
import com.hawkscheck.hawkscheck.model.TaskStatusEnum;

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
    private String mentorName;
    private List<String> assignedStudents; // nomes dos alunos
}

