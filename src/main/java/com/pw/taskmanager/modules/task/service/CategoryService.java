package com.pw.taskmanager.modules.task.service;

import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.dto.CategoryDto;
import com.pw.taskmanager.modules.task.entities.Category;
import com.pw.taskmanager.modules.task.errors.NotFoundCategory;
import com.pw.taskmanager.modules.task.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setNome(categoryDto.nome());
        Category save = categoryRepository.save(category);

        return new CategoryResponseDto(save.getId(), save.getNome());
    }
}
