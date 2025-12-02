package com.pw.taskmanager.modules.task.controller;

import com.pw.taskmanager.modules.shared.dto.PaginationResponseDto;
import com.pw.taskmanager.modules.shared.presentation.ErrorResponse;
import com.pw.taskmanager.modules.task.controller.response.TaskResponseDto;
import com.pw.taskmanager.modules.task.dto.task.TaskDto;
import com.pw.taskmanager.modules.task.dto.task.TaskUpdateDto;
import com.pw.taskmanager.modules.task.enums.Priority;
import com.pw.taskmanager.modules.task.enums.Status;
import com.pw.taskmanager.modules.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tasks", description = "Operações de Tarefa")
@RestController()
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation(
            summary = "Criar Tarefa",
            description = "Criar uma nova Tarefa",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponseDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @Operation(
            summary = "Atualizar uma Tarefa",
            description = "Atualizar os dados de uma Tarefa existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualização realizada com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Recurso não encontrado (categoria ou tarefa)",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = {
                                            @ExampleObject(
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
                                            ),
                                            @ExampleObject(
                                                    name = "TaskNotFoundExample",
                                                    value = """
                                                            {
                                                              "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                              "timestamp": "2025-12-02T12:34:56.789+00:00",
                                                              "status": 404,
                                                              "error": "Not Found",
                                                              "message": "Tarefa não encontrada",
                                                              "path": "/task/99"
                                                            }
                                                            """
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição inválida")
            }
    )
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto updateTask(@PathVariable Long id, @RequestBody TaskUpdateDto taskDto) {
        return taskService.updateTask(id, taskDto);
    }

    @Operation(
            summary = "Buscar Tarefa",
            description = "Buscar uma Tarefa Existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Busca feito com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarefa não encontrada",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "TaskNotFoundExample",
                                            value = """
                                                    {
                                                      "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                      "timestamp": "2025-12-02T12:34:56.789+00:00",
                                                      "status": 404,
                                                      "error": "Not Found",
                                                      "message": "Tarefa não encontrada",
                                                      "path": "/task/99"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Requisição invalida")
            }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponseDto findTask(@PathVariable Long id) {
        return taskService.getTask(id);
    }

    @Operation(
            summary = "Listar Tarefa",
            description = "Listar Tarefas",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listar feita com sucesso"),
            }
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginationResponseDto<List<TaskResponseDto>> findAllTasks(
            @Parameter(description = "Filtrar por status da tarefa")
            @RequestParam(required = false) Status status,
            @Parameter(description = "Filtrar por prioridade da tarefa")
            @RequestParam(required = false) Priority priority,
            @RequestParam(required = false) String name,
            @Parameter(description = "Filtrar pela data exata (yyyy-MM-dd)")
            @RequestParam(required = false) String data,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer perPage
    ) {
        return taskService.findMany(
                status,
                priority,
                name,
                data,
                page,
                perPage
        );
    }

    @Operation(
            summary = "Deletar",
            description = "Deletar uma tarefa",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Delete feito com sucesso"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tarefa não encontrado",
                            content = @Content(
                                    schema = @Schema(implementation = ErrorResponse.class),
                                    examples = @ExampleObject(
                                            name = "TaskNotFoundExample",
                                            value = """
                                                    {
                                                      "requestId": "123e4567-e89b-12d3-a456-426614174000",
                                                      "timestamp": "2025-12-02T12:34:56.789+00:00",
                                                      "status": 404,
                                                      "error": "Not Found",
                                                      "message": "Tarefa não encontrada",
                                                      "path": "/task/99"
                                                    }
                                                    """
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
        return "Tarefa deletada com sucesso";
    }
}
