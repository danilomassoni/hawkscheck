package com.hawkscheck.hawkscheck.dto;

import lombok.Data;

@Data
public class FrequencyReportDTO {
    private Long studentId;
    private String studentName;
    private String teamName;
    private long total; 
    private long present;
    private long absent;
    private double percentage; 
}
