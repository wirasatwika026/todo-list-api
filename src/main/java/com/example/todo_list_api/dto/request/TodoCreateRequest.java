package com.example.todo_list_api.dto.request;

import com.example.todo_list_api.model.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Request untuk membuat todo baru")
public class TodoCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters")
    @Schema(description = "Judul todo", example = "Belajar Spring Boot", required = true)
    private String title;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
    @Schema(description = "Deskripsi detail todo", example = "Mempelajari konsep REST API dengan Spring Boot")
    private String description;

    @NotNull(message = "Priority is required")
    @Schema(description = "Prioritas todo", example = "HIGH", required = true)
    private Todo.Priority priority;

    @Schema(description = "Tanggal dan waktu deadline", example = "2023-12-31T23:59:59")
    private LocalDateTime dueDate;
}
