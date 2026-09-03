package com.arshad.taskmanager.service;

import com.arshad.taskmanager.dto.TaskPatchRequest;
import com.arshad.taskmanager.dto.TaskRequest;
import com.arshad.taskmanager.dto.TaskResponse;
import com.arshad.taskmanager.dto.TaskStatusRequest;
import com.arshad.taskmanager.entity.Task;
import com.arshad.taskmanager.entity.TaskPriority;
import com.arshad.taskmanager.entity.TaskStatus;
import com.arshad.taskmanager.exception.TaskNotFoundException;
import com.arshad.taskmanager.repository.TaskRepository;
import com.arshad.taskmanager.specification.TaskSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        log.info("Creating task with title: {}", request.title());

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .priority(request.priority())
                .dueDate(request.dueDate())
                .build();

        Task savedTask = taskRepository.save(task);

        log.info("Task created successfully with id: {}", savedTask.getId());

        return TaskResponse.from(savedTask);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(
            TaskStatus status,
            TaskPriority priority,
            String title,
            LocalDateTime dueDateFrom,
            LocalDateTime dueDateTo,
            Pageable pageable
    ) {
        log.debug(
                "Fetching tasks. status={}, priority={}, title={}, dueDateFrom={}, dueDateTo={}, page={}, size={}",
                status,
                priority,
                title,
                dueDateFrom,
                dueDateTo,
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

        Specification<Task> specification = Specification
                .where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.hasPriority(priority))
                .and(TaskSpecification.titleContains(title))
                .and(TaskSpecification.dueDateFrom(dueDateFrom))
                .and(TaskSpecification.dueDateTo(dueDateTo));

        return taskRepository
                .findAll(specification, pageable)
                .map(TaskResponse::from);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        log.debug("Fetching task with id: {}", id);

        Task task = findTaskById(id);

        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        log.info("Updating task with id: {}", id);

        Task task = findTaskById(id);

        task.update(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        log.info("Task updated successfully with id: {}", id);

        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        log.info("Deleting task with id: {}", id);

        Task task = findTaskById(id);

        taskRepository.delete(task);

        log.info("Task deleted successfully with id: {}", id);
    }

    @Transactional
    public TaskResponse patchTask(Long id, TaskPatchRequest request) {
        log.info("Partially updating task with id: {}", id);

        Task task = findTaskById(id);

        task.patch(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        log.info("Task patched successfully with id: {}", id);

        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse updateStatus(Long id, TaskStatusRequest request) {
        log.info(
                "Updating task status. id={}, status={}",
                id,
                request.status()
        );

        Task task = findTaskById(id);

        task.changeStatus(request.status());

        log.info(
                "Task status updated successfully. id={}, status={}",
                id,
                request.status()
        );

        return TaskResponse.from(task);
    }

    private Task findTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Task not found with id: {}", id);
                    return new TaskNotFoundException(id);
                });
    }
}