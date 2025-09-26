package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.EquipmentRequestDTO;
import com.hawkscheck.hawkscheck.dto.EquipmentResponseDTO;
import com.hawkscheck.hawkscheck.model.Equipment;
import com.hawkscheck.hawkscheck.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentResponseDTO create(EquipmentRequestDTO dto) {
        Equipment equipment = Equipment.builder()
                .patrimonyNumber(dto.getPatrimonyNumber())
                .serialNumber(dto.getSerialNumber())
                .brandModel(dto.getBrandModel())
                .condition(dto.getCondition())
                .defaultLocation(dto.getDefaultLocation())
                .notes(dto.getNotes())
                .createdAt(LocalDateTime.now())
                .build();

        return toResponseDTO(equipmentRepository.save(equipment));
    }

    public List<EquipmentResponseDTO> findAll() {
        return equipmentRepository.findAll()
                .stream().map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EquipmentResponseDTO findById(Long id) {
        return equipmentRepository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));
    }

    public EquipmentResponseDTO update(Long id, EquipmentRequestDTO dto) {
        Equipment equipment = equipmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipment not found"));

        equipment.setPatrimonyNumber(dto.getPatrimonyNumber());
        equipment.setSerialNumber(dto.getSerialNumber());
        equipment.setBrandModel(dto.getBrandModel());
        equipment.setCondition(dto.getCondition());
        equipment.setDefaultLocation(dto.getDefaultLocation());
        equipment.setNotes(dto.getNotes());

        return toResponseDTO(equipmentRepository.save(equipment));
    }

    public void delete(Long id) {
        equipmentRepository.deleteById(id);
    }

    private EquipmentResponseDTO toResponseDTO(Equipment equipment) {
        return EquipmentResponseDTO.builder()
                .id(equipment.getId())
                .patrimonyNumber(equipment.getPatrimonyNumber())
                .serialNumber(equipment.getSerialNumber())
                .brandModel(equipment.getBrandModel())
                .condition(equipment.getCondition())
                .defaultLocation(equipment.getDefaultLocation())
                .notes(equipment.getNotes())
                .createdAt(equipment.getCreatedAt())
                .build();
    }
}
