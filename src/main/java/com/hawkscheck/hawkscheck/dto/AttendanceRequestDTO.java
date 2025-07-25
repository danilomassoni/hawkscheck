package com.hawkscheck.hawkscheck.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AttendanceRequestDTO {
    private Long studentId;
    private Long teamId;
    private LocalDate date;
    private boolean present;
    private List<AttendanceRecordDTO> records;
}
