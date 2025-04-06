package com.hawkscheck.hawkscheck.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudentResponseDTO {

    private Long id;
    private String name;
    private String rm;
    private String teamName;
    private Long teamId;

    public StudentResponseDTO(Long id, String name, String rm, String teamName) {
        this.id = id;
        this.name = name;
        this.rm = rm;
        this.teamName = teamName;
    }
    
}
