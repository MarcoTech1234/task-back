package com.pw.taskmanager.modules.task.service;

import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.controller.response.TaskResponseDto;
import com.pw.taskmanager.modules.task.dto.task.TaskDto;
import com.pw.taskmanager.modules.task.entities.Task;
import com.pw.taskmanager.modules.task.entities.Category;
import com.pw.taskmanager.modules.task.errors.NotFoundCategory;
import com.pw.taskmanager.modules.task.repositories.CategoryRepository;
import com.pw.taskmanager.modules.task.repositories.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public TaskResponseDto createTask(TaskDto taskDto) {
        Category category = categoryRepository.findById(taskDto.categoryId())
                .orElseThrow(NotFoundCategory::new);

        Task task = new Task();
        task.setNome(taskDto.nome());
        task.setDescricao(taskDto.descricao());
        task.setData(taskDto.data() != null ? taskDto.data().toString() : null);
        task.setStatus(taskDto.status());
        task.setCategory(category);

        // salva e retorna
        Task taskCreate = taskRepository.save(task);
        CategoryResponseDto catDto = new CategoryResponseDto(category.getId(), category.getNome());
        return new TaskResponseDto(taskCreate.getId(), taskCreate.getNome(), taskCreate.getDescricao(),
                taskCreate.getData(), taskCreate.getStatus(), taskCreate.getPriority(), catDto);
    }
}
