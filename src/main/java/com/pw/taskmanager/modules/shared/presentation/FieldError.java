package com.pw.taskmanager.modules.shared.presentation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FieldError", description = "Erro específico de um campo")
public class FieldError {
    @Schema(example = "nome")
    private String field;

    @Schema(example = "nome deve ser informado")
    private String message;

    public FieldError() {}
    public FieldError(String field, String message) {
        this.field = field;
        this.message = message;
    }
    public String getField() { return field; }
    public String getMessage() { return message; }
    public void setField(String f) { this.field = f; }
    public void setMessage(String m) { this.message = m; }
}
