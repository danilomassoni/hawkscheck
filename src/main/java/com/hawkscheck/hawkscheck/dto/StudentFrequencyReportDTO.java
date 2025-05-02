package com.hawkscheck.hawkscheck.dto;

import lombok.Data;

@Data
public class StudentFrequencyReportDTO {
    private Long studentId;
    private String StudentName;
    private String teamName;
    private Long total;
    private Long present;
    private Long absent;
    private double percentage;
}
