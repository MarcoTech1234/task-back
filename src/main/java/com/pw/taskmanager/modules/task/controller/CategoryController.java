package com.pw.taskmanager.modules.task.controller;

import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.dto.CategoryDto;
import com.pw.taskmanager.modules.task.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "Operações de Categoria")
@RestController()
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(
            summary = "Criar Categoria",
            description = "Criar uma nova Categoria",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDto createCategory(@RequestBody CategoryDto categoryDto) {
         return categoryService.createCategory(categoryDto);
    }
}
