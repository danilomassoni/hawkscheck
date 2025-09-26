package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.EquipmentConditionEnum;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class LoanResponseDTO {
    private Long id;
    private Long equipmentId;
    private String equipmentName;
    private Long professorId;
    private String professorName;
    private LocalDateTime borrowedAt;
    private LocalDateTime returnedAt;
    private boolean signatureCollected;
    private String notes;
    private EquipmentConditionEnum conditionOnReturn;
}
