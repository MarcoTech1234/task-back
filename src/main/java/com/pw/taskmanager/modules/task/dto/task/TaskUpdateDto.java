package com.pw.taskmanager.modules.task.dto.task;

import com.pw.taskmanager.modules.task.enums.Priority;
import com.pw.taskmanager.modules.task.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(name = "TaskUpdateDto", description = "Atualizar Tarefa")
public record TaskUpdateDto (
        @Size(max = 120, message = "nome deve ter no máximo 120 caracteres")
        @Schema(
                description = "Nome da tarefa",
                example = "Agendar Limpeza",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        String nome,

        @Size(max = 1000, message = "descrição deve ter no máximo 1000 caracteres")
        @Schema(
                description = "Descrição detalhada da tarefa",
                example = "Limpar o quarto e organizar a mesa",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        String descricao,

        @Schema(
                description = "Data da tarefa no formato ISO (yyyy-MM-dd)",
                example = "2025-12-11",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        LocalDate data,

        @Schema(
                description = "Status atual da tarefa",
                example = "PENDENTE",
                allowableValues = {"FEITO", "PENDENTE"},
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Status status,

        @Schema(
                description = "Prioridade da Tarefa",
                example = "BAIXA",
                allowableValues = {
                        "BAIXA",
                        "MEDIA",
                        "ALTA",
                        "CRITICA"
                },
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Priority priority,

        @Schema(
                description = "ID da categoria à qual a tarefa pertence",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long categoryId
){
}
