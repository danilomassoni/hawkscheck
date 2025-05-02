package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.PaperEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {
    private String token;
    private String name;
    private PaperEnum paperEnum;
}
