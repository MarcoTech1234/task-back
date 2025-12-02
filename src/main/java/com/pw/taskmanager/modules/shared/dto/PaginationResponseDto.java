package com.pw.taskmanager.modules.shared.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "PaginationResponseDto")
public class PaginationResponseDto<T> {

    @Schema(description = "Dados da página")
    private T data;

    @Schema(description = "Metadados da paginação")
    private PaginationMetaDto meta;

    public PaginationResponseDto() {}

    public PaginationResponseDto(T data, PaginationMetaDto meta) {
        this.data = data;
        this.meta = meta;
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }
    public PaginationMetaDto getMeta() { return meta; }
    public void setMeta(PaginationMetaDto meta) { this.meta = meta; }
}
