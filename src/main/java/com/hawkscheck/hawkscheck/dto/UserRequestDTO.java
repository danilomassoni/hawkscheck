package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.PaperEnum;

import lombok.Data;

@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;
    private PaperEnum paper;
}
