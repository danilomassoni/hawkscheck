package com.hawkscheck.hawkscheck.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

import com.hawkscheck.hawkscheck.model.TaskMessage;

@Data
@Builder
public class TaskMessageResponseDTO {
    private Long id;
    private String content;
    private String authorName;
    private Long authorId; 
    private LocalDateTime timestamp;

    
}