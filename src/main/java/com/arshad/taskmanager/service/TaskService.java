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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse createTask(TaskRequest request) {

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .priority(request.priority())
                .dueDate(request.dueDate())
                .build();

        Task savedTask = taskRepository.save(task);

        return TaskResponse.from(savedTask);
    }

    @Transactional(readOnly = true)
    public Page<TaskResponse> getAllTasks(
            TaskStatus status,
            TaskPriority priority,
            Pageable pageable
    ) {
        Specification<Task> specification = Specification
                .where(TaskSpecification.hasStatus(status))
                .and(TaskSpecification.hasPriority(priority));

        return taskRepository
                .findAll(specification, pageable)
                .map(TaskResponse::from);
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException(id));

        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse updateTask(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.update(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return TaskResponse.from(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        taskRepository.delete(task);
    }

    @Transactional
    public TaskResponse patchTask(Long id, TaskPatchRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.patch(
                request.title(),
                request.description(),
                request.priority(),
                request.dueDate()
        );

        return TaskResponse.from(task);
    }

    @Transactional
    public TaskResponse updateStatus(Long id, TaskStatusRequest request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));

        task.changeStatus(request.status());

        return TaskResponse.from(task);
    }
}
