package com.hawkscheck.hawkscheck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestDTO {
    @NotNull
    private Long recipientId;

    @NotBlank
    private String content;
}
