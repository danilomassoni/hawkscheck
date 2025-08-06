package com.hawkscheck.hawkscheck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TaskMessageRequestDTO {

    @NotBlank(message = "A mensagem não pode estar vazia")
    private String content;

    @NotNull(message = "O ID da tarefa é obrigatório")
    private Long taskId;

    @NotNull(message = "O ID do autor é obrigatório")
    private Long authorId;
}