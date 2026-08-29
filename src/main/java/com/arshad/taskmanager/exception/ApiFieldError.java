package com.arshad.taskmanager.exception;

public record ApiFieldError(
        String field,
        String message
) {
}
