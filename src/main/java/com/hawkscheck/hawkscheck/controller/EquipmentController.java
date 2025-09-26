package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.EquipmentRequestDTO;
import com.hawkscheck.hawkscheck.dto.EquipmentResponseDTO;
import com.hawkscheck.hawkscheck.service.EquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService equipmentService;

    @PostMapping
    public ResponseEntity<EquipmentResponseDTO> create(@RequestBody EquipmentRequestDTO dto) {
        return ResponseEntity.ok(equipmentService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<EquipmentResponseDTO>> findAll() {
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(equipmentService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipmentResponseDTO> update(@PathVariable Long id, @RequestBody EquipmentRequestDTO dto) {
        return ResponseEntity.ok(equipmentService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
