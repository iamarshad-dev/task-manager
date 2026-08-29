package com.arshad.taskmanager.dto;

import com.arshad.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public record TaskStatusRequest(

        @NotNull(message = "Status is required")
        TaskStatus status
) {
}
