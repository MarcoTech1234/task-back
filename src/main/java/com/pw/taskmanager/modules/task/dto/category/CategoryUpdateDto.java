package com.pw.taskmanager.modules.task.dto.category;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(name = "CategoryUpdateDto", description = "Atualizar Categoria")
public record CategoryUpdateDto (
        @Size(max = 100, message = "nome deve ter no máximo 120 caracteres")
        @Schema(
                description = "Nome da Categoria",
                example = "Limpeza",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String nome
) {

}
