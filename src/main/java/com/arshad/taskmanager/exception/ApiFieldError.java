package com.arshad.taskmanager.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

public record ApiFieldError(
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String field,
        String message
) {
}
