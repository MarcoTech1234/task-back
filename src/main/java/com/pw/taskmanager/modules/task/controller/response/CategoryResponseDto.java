package com.pw.taskmanager.modules.task.controller.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CategoryResponseDto", description = "Categoria")
public record CategoryResponseDto(
        @Schema(description = "ID da categoria", example = "1")
        Long id,
        @Schema(description = "Nome da categoria", example = "Limpeza")
        String nome
) {
}
