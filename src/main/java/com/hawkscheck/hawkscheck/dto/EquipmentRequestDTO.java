package com.hawkscheck.hawkscheck.dto;


import com.hawkscheck.hawkscheck.model.EquipmentConditionEnum;
import lombok.Data;

@Data
public class EquipmentRequestDTO {
    private String patrimonyNumber;
    private String serialNumber;
    private String brandModel;
    private EquipmentConditionEnum condition;
    private String defaultLocation;
    private String notes;
}