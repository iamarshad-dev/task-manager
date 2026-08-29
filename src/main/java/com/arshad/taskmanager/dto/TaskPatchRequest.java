package com.arshad.taskmanager.dto;

import com.arshad.taskmanager.entity.TaskPriority;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record TaskPatchRequest(

        @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
        String title,

        @Size(max = 1000, message = "Description must not exceed 1000 characters")
        String description,

        TaskPriority priority,

        @Future(message = "Due date must be in the future")
        LocalDateTime dueDate
) {
}
