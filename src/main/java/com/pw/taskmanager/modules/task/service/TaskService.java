package com.pw.taskmanager.modules.task.service;

import com.pw.taskmanager.modules.shared.dto.PaginationMetaDto;
import com.pw.taskmanager.modules.shared.dto.PaginationResponseDto;
import com.pw.taskmanager.modules.task.controller.response.CategoryResponseDto;
import com.pw.taskmanager.modules.task.controller.response.TaskResponseDto;
import com.pw.taskmanager.modules.task.dto.task.TaskDto;
import com.pw.taskmanager.modules.task.dto.task.TaskUpdateDto;
import com.pw.taskmanager.modules.task.entities.Task;
import com.pw.taskmanager.modules.task.entities.Category;
import com.pw.taskmanager.modules.task.enums.Priority;
import com.pw.taskmanager.modules.task.enums.Status;
import com.pw.taskmanager.modules.task.errors.NotFoundCategory;
import com.pw.taskmanager.modules.task.errors.NotFoundTask;
import com.pw.taskmanager.modules.task.mapper.TaskMapper;
import com.pw.taskmanager.modules.task.repositories.CategoryRepository;
import com.pw.taskmanager.modules.task.repositories.TaskRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TaskMapper taskMapper;
    private final KafkaTemplate<String, TaskResponseDto> kafkaTemplate;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository, TaskMapper taskMapper, KafkaTemplate<String, TaskResponseDto> kafkaTemplate) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
        this.taskMapper = taskMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public TaskResponseDto createTask(TaskDto taskDto) {
        Category category = categoryRepository.findById(taskDto.categoryId())
                .orElseThrow(NotFoundCategory::new);

        Task task = new Task();
        task.setNome(taskDto.nome());
        task.setDescricao(taskDto.descricao());
        task.setData(taskDto.data() != null ? taskDto.data().toString() : null);
        task.setStatus(taskDto.status());
        task.setPriority(taskDto.priority());
        task.setCategory(category);

        // salva e retorna
        Task taskCreate = taskRepository.save(task);
        CategoryResponseDto catDto = new CategoryResponseDto(category.getId(), category.getNome());
        return new TaskResponseDto(taskCreate.getId(), taskCreate.getNome(), taskCreate.getDescricao(),
                taskCreate.getData(), taskCreate.getStatus(), taskCreate.getPriority(), catDto);
    }

    public TaskResponseDto updateTask(Long id, TaskUpdateDto taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(NotFoundTask::new);

        if (taskDto.categoryId() != null) {
            Category category = categoryRepository.findById(taskDto.categoryId())
                    .orElseThrow(NotFoundCategory::new);
            existingTask.setCategory(category);
        }

        existingTask.toUpdate(taskDto);
        Task taskUpdate = taskRepository.save(existingTask);


        Category category = taskUpdate.getCategory();
        CategoryResponseDto catDto = new CategoryResponseDto(category.getId(), category.getNome());
        TaskResponseDto taskResponseDto = new TaskResponseDto(taskUpdate.getId(), taskUpdate.getNome(), taskUpdate.getDescricao(),
                taskUpdate.getData(), taskUpdate.getStatus(), taskUpdate.getPriority(), catDto);

        if (taskUpdate.getStatus() == Status.FEITO) {
            kafkaTemplate.send("email-processor", null, taskResponseDto);
        }

        return taskResponseDto;
    }

    public TaskResponseDto getTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(NotFoundTask::new);
        Category category = task.getCategory();
        CategoryResponseDto catDto = new CategoryResponseDto(category.getId(), category.getNome());
        return new TaskResponseDto(task.getId(), task.getNome(), task.getDescricao(),
                task.getData(), task.getStatus(), task.getPriority(), catDto);
    }

    public PaginationResponseDto<List<TaskResponseDto>> findMany(
            Status status,
            Priority priority,
            String nome,
            Long categoryId,
            String data,
            int page,
            int perPage,
            String sortBy,    // novo: "id" | "data" | "category" | outro campo
            String sortDir
    )
    {
        int pageIndex = Math.max(0, page - 1);

        // Normaliza sort
        String sortProperty = (sortBy == null || sortBy.isBlank()) ? "id" : sortBy.trim();
        boolean descending = "desc".equalsIgnoreCase(sortDir);

        Sort sort = Sort.by(descending ? Sort.Direction.DESC : Sort.Direction.ASC, sortProperty);

        Pageable pageable = PageRequest.of(pageIndex, perPage, sort);

        Specification<Task> spec = getTaskSpecification(status, priority, categoryId, nome, data);

        Page<Task> result = taskRepository.findAll(spec, pageable);

        List<TaskResponseDto> dtos = result.stream()
                .map(taskMapper::toDto)
                .toList();

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

    private static Specification<Task> getTaskSpecification(
            Status status,
            Priority priority,
            Long categoryId,
            String nome,
            String data
    ) {
        return (Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // filtro por status (enum)
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            // filtro por priority (enum)
            if (priority != null) {
                predicates.add(cb.equal(root.get("priority"), priority));
            }

            if (categoryId != null) {
                predicates.add(cb.equal(root.get("category").get("id"), categoryId));
            }

            // filtro por nome (contains, case-insensitive)
            if (nome != null && !nome.isBlank()) {
                String pattern = "%" + nome.trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("nome")), pattern));
            }

            // filtro por data (ex.: "2025-12-11") - usa equality porque sua entidade armazena String
            if (data != null && !data.isBlank()) {
                String d = data.trim();
                predicates.add(cb.equal(root.get("data"), d));
            }

            if (predicates.isEmpty()) {
                return cb.conjunction();
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    public void deleteById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(NotFoundTask::new);
        taskRepository.delete(task);
    }
}
