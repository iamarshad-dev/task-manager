package com.arshad.taskmanager.service;

import com.arshad.taskmanager.dto.TaskRequest;
import com.arshad.taskmanager.dto.TaskResponse;
import com.arshad.taskmanager.entity.Task;
import com.arshad.taskmanager.exception.TaskNotFoundException;
import com.arshad.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new TaskNotFoundException(id));

        return TaskResponse.from(task);
    }
}
