package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.PaperEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private PaperEnum paper;
}
