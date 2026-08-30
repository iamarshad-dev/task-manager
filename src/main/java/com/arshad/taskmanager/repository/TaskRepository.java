package com.arshad.taskmanager.repository;

import com.arshad.taskmanager.entity.Task;
import com.arshad.taskmanager.entity.TaskPriority;
import com.arshad.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TaskRepository
        extends JpaRepository<Task, Long>,
        JpaSpecificationExecutor<Task> {
}
