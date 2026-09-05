package com.arshad.taskmanager.dto;

import com.arshad.taskmanager.entity.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskRequest(

        @Schema(
                description = "Task title",
                example = "Learn Docker"
        )
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must not exceed 255 characters")
        String title,

        @Schema(
                description = "Task description",
                example = "Practice Docker Compose"
        )
        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        @Schema(
                description = "Task priority",
                example = "HIGH"
        )
        @NotNull(message = "Priority is required")
        TaskPriority priority,

        @Schema(
                description = "Task due date",
                example = "2026-09-20T10:00:00"
        )
        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate

) {}