package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.SpaceRequestDTO;
import com.hawkscheck.hawkscheck.dto.SpaceResponseDTO;
import com.hawkscheck.hawkscheck.model.Space;
import com.hawkscheck.hawkscheck.repository.SpaceRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    // Criar espaço
    public SpaceResponseDTO create(SpaceRequestDTO dto) {
        Space space = new Space();
        space.setName(dto.getName());
        space.setLocation(dto.getLocation());
        space.setUsageType(dto.getUsageType());
        space.setDescription(dto.getDescription());
        space.setActive(true);

        space = spaceRepository.save(space);
        return mapToResponseDTO(space);
    }

    // Listar todos
    public List<SpaceResponseDTO> getAll() {
        return spaceRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // Buscar por ID
    public SpaceResponseDTO getById(Long id) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado"));
        return mapToResponseDTO(space);
    }

    // Atualizar espaço
    public SpaceResponseDTO update(Long id, SpaceRequestDTO dto) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado"));

        space.setName(dto.getName());
        space.setLocation(dto.getLocation());
        space.setUsageType(dto.getUsageType());
        space.setDescription(dto.getDescription());

        space = spaceRepository.save(space);
        return mapToResponseDTO(space);
    }

    // Desativar espaço (soft delete)
    public void deactivate(Long id) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Espaço não encontrado"));

        space.setActive(false);
        spaceRepository.save(space);
    }

    // Mapper auxiliar
    private SpaceResponseDTO mapToResponseDTO(Space space) {
        SpaceResponseDTO dto = new SpaceResponseDTO();
        dto.setId(space.getId());
        dto.setName(space.getName());
        dto.setLocation(space.getLocation());
        dto.setUsageType(space.getUsageType());
        dto.setDescription(space.getDescription());
        dto.setActive(space.getActive());
        dto.setCreatedAt(space.getCreatedAt());
        dto.setUpdatedAt(space.getUpdatedAt());
        return dto;
    }
}
