package com.pw.taskmanager.modules.task.service;

import com.pw.taskmanager.modules.shared.dto.PaginationMetaDto;
import com.pw.taskmanager.modules.shared.dto.PaginationResponseDto;
import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.dto.category.CategoryDto;
import com.pw.taskmanager.modules.task.dto.category.CategoryUpdateDto;
import com.pw.taskmanager.modules.task.entities.Category;
import com.pw.taskmanager.modules.task.errors.NotFoundCategory;
import com.pw.taskmanager.modules.task.mapper.CategoryMapper;
import com.pw.taskmanager.modules.task.repositories.CategoryRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @CachePut(value = "CATEGORY_CACHE", key = "#result.id()")
    public CategoryResponseDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setNome(categoryDto.nome());
        Category save = categoryRepository.save(category);

        return new CategoryResponseDto(save.getId(), save.getNome());
    }

    @CachePut(value = "CATEGORY_CACHE", key = "#result.id()")
    public CategoryResponseDto updateCategory(Long id, CategoryUpdateDto categoryUpdateDto) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(NotFoundCategory::new);

        existing.toUpdate(categoryUpdateDto);
        Category saved = categoryRepository.save(existing);

        return new CategoryResponseDto(saved.getId(), saved.getNome());
    }

    @Cacheable(value = "CATEGORY_CACHE", key = "#id")
    public CategoryResponseDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundCategory::new);
        return new CategoryResponseDto(category.getId(), category.getNome());
    }

    public PaginationResponseDto<List<CategoryResponseDto>> findMany(String name, String search, int page, int perPage) {

        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, perPage, Sort.by("id").ascending());

        Specification<Category> spec = getCategorySpecification(name, search);

        Page<Category> result = categoryRepository.findAll(spec, pageable);

        List<CategoryResponseDto> dtos = result.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());

        PaginationMetaDto meta = new PaginationMetaDto();
        meta.setCurrentPage(page);
        meta.setPageCount(result.getTotalPages());
        meta.setTotalCount(result.getTotalElements());
        meta.setFirstPage(result.isFirst());
        meta.setLastPage(result.isLast());
        meta.setPreviousPage(result.hasPrevious() ? page - 1 : null);
        meta.setNextPage(result.hasNext() ? page + 1 : null);

        return new PaginationResponseDto<>(dtos, meta);
    }

    private static Specification<Category> getCategorySpecification(String name, String search) {
        return (Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {

            // Lista de filtros
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por name (campo da categoria)
            if (name != null && !name.isBlank()) {
                String pattern = "%" + name.trim().toLowerCase() + "%";
                predicates.add(
                        cb.like(cb.lower(root.get("nome")), pattern)
                );
            }

            // Filtro de texto livre search (somente em categoria.nome)
            if (search != null && !search.isBlank()) {
                String pattern = "%" + search.trim().toLowerCase() + "%";
                predicates.add(
                        cb.like(cb.lower(root.get("nome")), pattern)
                );
            }

            // Se não existir nenhum critério, retorna "true"
            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    @CacheEvict(value = "CATEGORY_CACHE", key = "#id")
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(NotFoundCategory::new);
        categoryRepository.delete(category);
    }
}
