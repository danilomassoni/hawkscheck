package com.hawkscheck.hawkscheck.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceResponseDTO {
    private Long id;
    private Long studentId;
    private String studentName;
    private Long teamId;
    private String teamName;
    private LocalDate date;
    private boolean present;
}
