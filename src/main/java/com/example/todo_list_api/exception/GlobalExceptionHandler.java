package com.example.todo_list_api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            WebRequest request) {

        log.error("Resource not found: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "RESOURCE_NOT_FOUND",
                HttpStatus.NOT_FOUND.value(),
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {

        log.error("Validation error: {}", ex.getMessage());

        BindingResult bindingResult = ex.getBindingResult();
        List<ErrorResponse.ValidationError> validationErrors = bindingResult
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse.ValidationError(
                        error.getField(),
                        error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "Validation failed",
                "VALIDATION_ERROR",
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false).replace("uri=", ""));
        errorResponse.setValidationErrors(validationErrors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException ex,
            WebRequest request) {

        log.error("Illegal argument: {}", ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getMessage(),
                "ILLEGAL_ARGUMENT",
                HttpStatus.BAD_REQUEST.value(),
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            Exception ex,
            WebRequest request) {

        log.error("Unexpected error occurred: ", ex);

        ErrorResponse errorResponse = new ErrorResponse(
                "An unexpected error occurred",
                "INTERNAL_SERVER_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
