package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.SpaceRequestDTO;
import com.hawkscheck.hawkscheck.dto.SpaceResponseDTO;
import com.hawkscheck.hawkscheck.service.SpaceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @PostMapping
    public SpaceResponseDTO create(@RequestBody SpaceRequestDTO dto) {
        return spaceService.create(dto);
    }

    @GetMapping
    public List<SpaceResponseDTO> getAll() {
        return spaceService.getAll();
    }

    @GetMapping("/{id}")
    public SpaceResponseDTO getById(@PathVariable Long id) {
        return spaceService.getById(id);
    }

    @PutMapping("/{id}")
    public SpaceResponseDTO update(@PathVariable Long id, @RequestBody SpaceRequestDTO dto) {
        return spaceService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deactivate(@PathVariable Long id) {
        spaceService.deactivate(id);
    }
}
