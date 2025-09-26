package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.EquipmentConditionEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanRequestDTO {
    private Long equipmentId;
    private Long professorId;
    private boolean signatureCollected;
    private String notes;
    private EquipmentConditionEnum conditionOnReturn; 
}
