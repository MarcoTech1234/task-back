package com.pw.taskmanager.modules.task.controller;

import com.pw.taskmanager.modules.shared.dto.PaginationResponseDto;
import com.pw.taskmanager.modules.shared.presentation.ErrorResponse;
import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.dto.category.CategoryDto;
import com.pw.taskmanager.modules.task.dto.category.CategoryUpdateDto;
import com.pw.taskmanager.modules.task.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category", description = "Operações de Categoria")
@RestController()
@RequestMapping("/category")
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

    @Operation(
            summary = "Atualizar Categoria",
            description = "Atualizar uma Categoria Existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "CategoryNotFoundExample",
                                            value = """
                                                    {
                                                      "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                      "timestamp": "2025-12-02T12:34:56.789+00:00",
                                                      "status": 404,
                                                      "error": "Not Found",
                                                      "message": "Categoria não encontrada",
                                                      "path": "/category/99"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto updateCategory(@RequestBody CategoryUpdateDto categoryUpdateDto, @PathVariable Long id) {
        return categoryService.updateCategory(id, categoryUpdateDto);
    }

    @Operation(
            summary = "Buscar Categoria",
            description = "Buscar uma Categoria Existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca feita com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "CategoryNotFoundExample",
                                            value = """
                                                    {
                                                      "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                      "timestamp": "2025-12-02T12:34:56.789+00:00",
                                                      "status": 404,
                                                      "error": "Not Found",
                                                      "message": "Categoria não encontrada",
                                                      "path": "/category/99"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponseDto findCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @Operation(
            summary = "Listar Categoria",
            description = "Listar uma Categoria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listar feita com sucesso"),
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseDto<List<CategoryResponseDto>> listCategories(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer perPage
    ){
        return categoryService.findMany(name, search, page, perPage);
    }

    @Operation(
            summary = "Deletar",
            description = "Deletar uma categoria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete feito com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Categoria não encontrada",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "CategoryNotFoundExample",
                                            value = """
                                                    {
                                                      "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                      "timestamp": "2025-12-02T12:34:56.789+00:00",
                                                      "status": 404,
                                                      "error": "Not Found",
                                                      "message": "Categoria não encontrada",
                                                      "path": "/category/99"
                                                    }
                                                    """
                                    )
                            )
                    ),
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCategory(@PathVariable Long id){
        categoryService.deleteById(id);
        return "Categoria deletada com sucesso";
    }
}
