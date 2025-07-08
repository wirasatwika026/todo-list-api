package com.example.todo_list_api.dto.request;

import com.example.todo_list_api.model.Todo;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TodoUpdateRequest {

    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    private String description;

    private Boolean completed;

    private Todo.Priority priority;

    private LocalDateTime dueDate;
}
