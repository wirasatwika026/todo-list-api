package com.example.todo_list_api.controller;

import com.example.todo_list_api.dto.request.TodoCreateRequest;
import com.example.todo_list_api.dto.request.TodoUpdateRequest;
import com.example.todo_list_api.dto.response.ApiResponse;
import com.example.todo_list_api.dto.response.TodoResponse;
import com.example.todo_list_api.model.Todo;
import com.example.todo_list_api.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Todo Management", description = "API untuk mengelola daftar tugas (Todo List)")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @Operation(summary = "Membuat todo baru", description = "Membuat item todo baru dengan title, description, priority, dan due date")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Todo berhasil dibuat"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Data input tidak valid")
    })
    public ResponseEntity<ApiResponse<TodoResponse>> createTodo(
            @Valid @RequestBody @Parameter(description = "Data todo yang akan dibuat") TodoCreateRequest request) {

        log.info("Creating new todo: {}", request.getTitle());
        TodoResponse todoResponse = todoService.createTodo(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Todo created successfully", todoResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Mendapatkan todo berdasarkan ID", description = "Mengambil detail todo berdasarkan ID yang diberikan")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo ditemukan"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Todo tidak ditemukan")
    })
    public ResponseEntity<ApiResponse<TodoResponse>> getTodoById(
            @PathVariable @Parameter(description = "ID todo yang akan diambil") Long id) {
        log.info("Fetching todo with ID: {}", id);
        TodoResponse todoResponse = todoService.getTodoById(id);

        return ResponseEntity.ok(ApiResponse.success(todoResponse));
    }

    @GetMapping
    @Operation(summary = "Mendapatkan semua todo", description = "Mengambil daftar semua todo dengan opsi filter berdasarkan status, priority, atau pencarian")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Daftar todo berhasil diambil")
    })
    public ResponseEntity<ApiResponse<List<TodoResponse>>> getAllTodos(
            @RequestParam(required = false) @Parameter(description = "Filter berdasarkan status completion (true/false)") Boolean completed,
            @RequestParam(required = false) @Parameter(description = "Filter berdasarkan priority (LOW/MEDIUM/HIGH)") Todo.Priority priority,
            @RequestParam(required = false) @Parameter(description = "Pencarian berdasarkan judul todo") String search) {

        List<TodoResponse> todos;

        if (search != null && !search.trim().isEmpty()) {
            log.info("Searching todos by title: {}", search);
            todos = todoService.searchTodosByTitle(search);
        } else if (completed != null) {
            log.info("Fetching todos by completion status: {}", completed);
            todos = todoService.getTodosByStatus(completed);
        } else if (priority != null) {
            log.info("Fetching todos by priority: {}", priority);
            todos = todoService.getTodosByPriority(priority);
        } else {
            log.info("Fetching all todos");
            todos = todoService.getAllTodos();
        }

        return ResponseEntity.ok(ApiResponse.success(todos));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update todo", description = "Memperbarui todo yang sudah ada berdasarkan ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo berhasil diperbarui"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Todo tidak ditemukan"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Data input tidak valid")
    })
    public ResponseEntity<ApiResponse<TodoResponse>> updateTodo(
            @PathVariable @Parameter(description = "ID todo yang akan diperbarui") Long id,
            @Valid @RequestBody @Parameter(description = "Data todo yang akan diperbarui") TodoUpdateRequest request) {

        log.info("Updating todo with ID: {}", id);
        TodoResponse todoResponse = todoService.updateTodo(id, request);

        return ResponseEntity.ok(ApiResponse.success("Todo updated successfully", todoResponse));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Hapus todo", description = "Menghapus todo berdasarkan ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Todo berhasil dihapus"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Todo tidak ditemukan")
    })
    public ResponseEntity<ApiResponse<Void>> deleteTodo(
            @PathVariable @Parameter(description = "ID todo yang akan dihapus") Long id) {
        log.info("Deleting todo with ID: {}", id);
        todoService.deleteTodo(id);

        return ResponseEntity.ok(ApiResponse.success("Todo deleted successfully", null));
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<TodoResponse>> markAsCompleted(@PathVariable Long id) {
        log.info("Marking todo as completed with ID: {}", id);
        TodoResponse todoResponse = todoService.markAsCompleted(id);

        return ResponseEntity.ok(ApiResponse.success("Todo marked as completed", todoResponse));
    }

    @PatchMapping("/{id}/incomplete")
    public ResponseEntity<ApiResponse<TodoResponse>> markAsIncomplete(@PathVariable Long id) {
        log.info("Marking todo as incomplete with ID: {}", id);
        TodoResponse todoResponse = todoService.markAsIncomplete(id);

        return ResponseEntity.ok(ApiResponse.success("Todo marked as incomplete", todoResponse));
    }

    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<TodoResponse>>> getOverdueTodos() {
        log.info("Fetching overdue todos");
        List<TodoResponse> todos = todoService.getOverdueTodos();

        return ResponseEntity.ok(ApiResponse.success(todos));
    }

    @GetMapping("/due-between")
    public ResponseEntity<ApiResponse<List<TodoResponse>>> getTodosDueBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        log.info("Fetching todos due between {} and {}", start, end);
        List<TodoResponse> todos = todoService.getTodosDueBetween(start, end);

        return ResponseEntity.ok(ApiResponse.success(todos));
    }

    @GetMapping("/statistics")
    @Operation(summary = "Statistik todo", description = "Mendapatkan statistik todo (total, completed, pending)")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Statistik berhasil diambil")
    })
    public ResponseEntity<ApiResponse<Map<String, Long>>> getTodoStatistics() {
        log.info("Fetching todo statistics");

        long completedCount = todoService.getCompletedTodosCount();
        long pendingCount = todoService.getPendingTodosCount();
        long totalCount = completedCount + pendingCount;

        Map<String, Long> statistics = Map.of(
                "total", totalCount,
                "completed", completedCount,
                "pending", pendingCount);

        return ResponseEntity.ok(ApiResponse.success(statistics));
    }
}
