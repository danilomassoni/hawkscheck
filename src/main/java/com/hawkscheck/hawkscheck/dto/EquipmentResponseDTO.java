package com.hawkscheck.hawkscheck.dto;


import com.hawkscheck.hawkscheck.model.EquipmentConditionEnum;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EquipmentResponseDTO {
    private Long id;
    private String patrimonyNumber;
    private String serialNumber;
    private String brandModel;
    private EquipmentConditionEnum condition;
    private String defaultLocation;
    private String notes;
    private LocalDateTime createdAt;
}