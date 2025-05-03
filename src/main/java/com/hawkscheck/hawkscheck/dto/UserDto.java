package com.hawkscheck.hawkscheck.dto;

import com.hawkscheck.hawkscheck.model.PaperEnum;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String email;
    private String password;
    private PaperEnum paperEnum;
}
