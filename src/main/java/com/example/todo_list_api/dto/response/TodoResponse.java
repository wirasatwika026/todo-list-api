package com.example.todo_list_api.dto.response;

import com.example.todo_list_api.model.Todo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Response data todo")
public class TodoResponse {

    @Schema(description = "ID unik todo", example = "1")
    private Long id;

    @Schema(description = "Judul todo", example = "Belajar Spring Boot")
    private String title;

    @Schema(description = "Deskripsi detail todo", example = "Mempelajari konsep REST API dengan Spring Boot")
    private String description;

    @Schema(description = "Status completion todo", example = "false")
    private Boolean completed;

    @Schema(description = "Prioritas todo", example = "HIGH")
    private Todo.Priority priority;

    @Schema(description = "Tanggal dan waktu deadline", example = "2023-12-31T23:59:59")
    private LocalDateTime dueDate;

    @Schema(description = "Tanggal dan waktu pembuatan", example = "2023-07-08T10:30:00")
    private LocalDateTime createdAt;

    @Schema(description = "Tanggal dan waktu terakhir diperbarui", example = "2023-07-08T10:30:00")
    private LocalDateTime updatedAt;

    @Schema(description = "ID user pemilik todo", example = "1")
    private Long userId;

    @Schema(description = "Username pemilik todo", example = "john_doe")
    private String username;

    public static TodoResponse fromEntity(Todo todo) {
        return new TodoResponse(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.getCompleted(),
                todo.getPriority(),
                todo.getDueDate(),
                todo.getCreatedAt(),
                todo.getUpdatedAt(),
                todo.getUser() != null ? todo.getUser().getId() : null,
                todo.getUser() != null ? todo.getUser().getUsername() : null);
    }
}
