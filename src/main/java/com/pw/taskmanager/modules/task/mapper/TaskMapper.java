package com.pw.taskmanager.modules.task.mapper;

import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.controller.response.TaskResponseDto;
import com.pw.taskmanager.modules.task.entities.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponseDto toDto(Task t) {
        if (t == null) return null;
        return new TaskResponseDto(
                t.getId(),
                t.getNome(),
                t.getDescricao(),
                t.getData(),
                t.getStatus(),
                t.getPriority(),
                new CategoryResponseDto(
                        t.getCategory().getId(),
                        t.getCategory().getNome()
                )
        );
    }
}
