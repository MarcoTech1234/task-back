package com.pw.taskmanager.modules.shared.presentation;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = "ErrorResponse", description = "Formato padrão de resposta de erro")
public class ErrorResponse {
    @Schema(description = "ID de correlação da requisição")
    private String requestId;

    @Schema(description = "Timestamp do erro")
    private OffsetDateTime timestamp;

    @Schema(description = "Código HTTP")
    private int status;

    @Schema(description = "Texto do status HTTP")
    private String error;

    @Schema(description = "Mensagem legível do erro")
    private String message;

    @Schema(description = "Caminho da requisição")
    private String path;

    @Schema(description = "Erros de campo (quando aplicável)")
    private List<FieldError> errors;
    // construtores/getters/setters (pode gerar com Lombok se preferir)
    public ErrorResponse() {}

    public ErrorResponse(String requestId, OffsetDateTime timestamp, int status, String error, String message, String path, List<FieldError> errors) {
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    // getters e setters...
    public String getRequestId() { return requestId; }
    public OffsetDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
    public List<FieldError> getErrors() { return errors; }

    public void setRequestId(String requestId) { this.requestId = requestId; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
    public void setStatus(int status) { this.status = status; }
    public void setError(String error) { this.error = error; }
    public void setMessage(String message) { this.message = message; }
    public void setPath(String path) { this.path = path; }
    public void setErrors(List<FieldError> errors) { this.errors = errors; }
}
