package com.hawkscheck.hawkscheck.dto;

import lombok.Data;

@Data
public class AttendanceRecordDTO {
    private Long studentId;
    private boolean present;
}
