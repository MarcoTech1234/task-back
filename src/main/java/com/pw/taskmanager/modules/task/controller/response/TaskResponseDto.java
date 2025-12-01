package com.pw.taskmanager.modules.task.controller.response;

import com.pw.taskmanager.modules.task.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TaskResponseDto", description = "Tarefa")
public record TaskResponseDto(
        @Schema(description = "ID da Tarefa", example = "1")
        Long id,
        @Schema(description = "Nome da Tarefa", example = "Agendar Limpeza")
        String nome,
        @Schema(description = "Descrição da Tarefa", example = "Limpar o quarto e organizar a mesa")
        String descricao,
        @Schema(description = "Data da Tarefa", example = "2025-12-11")
        String data,
        @Schema(description = "Status da Tarefa", example = "PENDENTE")
        Status status,
        CategoryResponseDto category
) {
}
