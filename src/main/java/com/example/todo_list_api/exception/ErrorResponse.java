package com.example.todo_list_api.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {

    private boolean success;
    private String message;
    private String error;
    private int status;
    private String timestamp;
    private String path;
    private List<ValidationError> validationErrors;

    public ErrorResponse() {
        this.success = false;
        this.timestamp = LocalDateTime.now().toString();
    }

    public ErrorResponse(String message, String error, int status, String path) {
        this();
        this.message = message;
        this.error = error;
        this.status = status;
        this.path = path;
    }

    @Data
    public static class ValidationError {
        private String field;
        private String message;

        public ValidationError(String field, String message) {
            this.field = field;
            this.message = message;
        }
    }
}
