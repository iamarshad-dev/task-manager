package com.arshad.taskmanager.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tools.jackson.databind.exc.InvalidFormatException;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
        List<ApiFieldError> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiFieldError(
                        error.getField(),
                        error.getDefaultMessage()
                ))
                .toList();

        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI(),
                errors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);

    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTaskNotFoundException(
            TaskNotFoundException exception,
            HttpServletRequest request
    ) {

        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                request.getRequestURI(),
                List.of(
                        new ApiFieldError(
                                null,
                                exception.getMessage()
                        )
                )
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpServletRequest request
    ) {
        Throwable cause = exception.getCause();

        while (cause != null) {

            if (cause instanceof InvalidFormatException invalidFormatException) {

                Class<?> targetType = invalidFormatException.getTargetType();

                if (targetType != null && targetType.isEnum()) {

                    String field = invalidFormatException.getPath().isEmpty()
                            ? null
                            : invalidFormatException.getPath()
                            .getLast()
                            .getPropertyName();

                    String allowedValues = Arrays.stream(targetType.getEnumConstants())
                            .map(Object::toString)
                            .collect(Collectors.joining(", "));

                    ApiErrorResponse response = new ApiErrorResponse(
                            Instant.now(),
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            request.getRequestURI(),
                            List.of(
                                    new ApiFieldError(
                                            field,
                                            "Invalid value '%s'. Allowed values: %s"
                                                    .formatted(
                                                            invalidFormatException.getValue(),
                                                            allowedValues
                                                    )
                                    )
                            )
                    );

                    return ResponseEntity
                            .badRequest()
                            .body(response);
                }
            }

            cause = cause.getCause();
        }

        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI(),
                List.of(
                        new ApiFieldError(
                                null,
                                "Malformed request body"
                        )
                )
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException exception,
            HttpServletRequest request
    ) {
        Class<?> requiredType = exception.getRequiredType();

        if (requiredType != null && requiredType.isEnum()) {

            String allowedValues = Arrays.stream(requiredType.getEnumConstants())
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));

            ApiErrorResponse response = new ApiErrorResponse(
                    Instant.now(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    request.getRequestURI(),
                    List.of(
                            new ApiFieldError(
                                    exception.getName(),
                                    "Invalid value '%s'. Allowed values: %s"
                                            .formatted(
                                                    exception.getValue(),
                                                    allowedValues
                                            )
                            )
                    )
            );

            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        ApiErrorResponse response = new ApiErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getRequestURI(),
                List.of(
                        new ApiFieldError(
                                exception.getName(),
                                "Invalid value '%s'"
                                        .formatted(exception.getValue())
                        )
                )
        );

        return ResponseEntity
                .badRequest()
                .body(response);
    }
}
