package com.hawkscheck.hawkscheck.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpaceResponseDTO {
    private Long id;
    private String name;
    private String location;
    private String usageType;
    private String description;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
