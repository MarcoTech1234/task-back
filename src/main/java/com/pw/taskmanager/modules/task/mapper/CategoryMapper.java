package com.pw.taskmanager.modules.task.mapper;

import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.entities.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponseDto toDto(Category c) {
        if (c == null) return null;
        return new CategoryResponseDto(
                c.getId(),
                c.getNome()
        );
    }
}

