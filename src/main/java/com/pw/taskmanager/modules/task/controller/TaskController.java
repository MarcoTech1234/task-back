package com.pw.taskmanager.modules.task.controller;

import com.pw.taskmanager.modules.task.controller.response.TaskResponseDto;
import com.pw.taskmanager.modules.task.dto.task.TaskDto;
import com.pw.taskmanager.modules.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Tasks", description = "Operações de Tarefa")
@RestController()
@RequestMapping("/api/task")
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
}
