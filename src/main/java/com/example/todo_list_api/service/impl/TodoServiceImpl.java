package com.example.todo_list_api.service.impl;

import com.example.todo_list_api.dto.request.TodoCreateRequest;
import com.example.todo_list_api.dto.request.TodoUpdateRequest;
import com.example.todo_list_api.dto.response.TodoResponse;
import com.example.todo_list_api.exception.ResourceNotFoundException;
import com.example.todo_list_api.model.Todo;
import com.example.todo_list_api.model.User;
import com.example.todo_list_api.repository.TodoRepository;
import com.example.todo_list_api.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new RuntimeException("User not authenticated");
    }

    @Override
    public TodoResponse createTodo(TodoCreateRequest request) {
        User currentUser = getCurrentUser();
        log.info("Creating new todo with title: {} for user: {}", request.getTitle(), currentUser.getUsername());

        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setPriority(request.getPriority());
        todo.setDueDate(request.getDueDate());
        todo.setCompleted(false);
        todo.setUser(currentUser);

        Todo savedTodo = todoRepository.save(todo);
        log.info("Todo created successfully with ID: {} for user: {}", savedTodo.getId(), currentUser.getUsername());

        return TodoResponse.fromEntity(savedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public TodoResponse getTodoById(Long id) {
        User currentUser = getCurrentUser();
        log.info("Fetching todo with ID: {} for user: {}", id, currentUser.getUsername());

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with ID: " + id));

        // Check if todo belongs to current user
        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Todo not found with ID: " + id);
        }

        return TodoResponse.fromEntity(todo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getAllTodos() {
        User currentUser = getCurrentUser();
        log.info("Fetching all todos for user: {}", currentUser.getUsername());

        return todoRepository.findByUserId(currentUser.getId())
                .stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse updateTodo(Long id, TodoUpdateRequest request) {
        User currentUser = getCurrentUser();
        log.info("Updating todo with ID: {} for user: {}", id, currentUser.getUsername());

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with ID: " + id));

        // Check if todo belongs to current user
        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Todo not found with ID: " + id);
        }

        // Update only non-null fields
        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getCompleted() != null) {
            todo.setCompleted(request.getCompleted());
        }
        if (request.getPriority() != null) {
            todo.setPriority(request.getPriority());
        }
        if (request.getDueDate() != null) {
            todo.setDueDate(request.getDueDate());
        }

        Todo updatedTodo = todoRepository.save(todo);
        log.info("Todo updated successfully with ID: {}", updatedTodo.getId());

        return TodoResponse.fromEntity(updatedTodo);
    }

    @Override
    public void deleteTodo(Long id) {
        User currentUser = getCurrentUser();
        log.info("Deleting todo with ID: {} for user: {}", id, currentUser.getUsername());

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with ID: " + id));

        // Check if todo belongs to current user
        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Todo not found with ID: " + id);
        }

        todoRepository.deleteById(id);
        log.info("Todo deleted successfully with ID: {} for user: {}", id, currentUser.getUsername());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByStatus(Boolean completed) {
        User currentUser = getCurrentUser();
        log.info("Fetching todos by completion status: {} for user: {}", completed, currentUser.getUsername());

        return todoRepository.findByCompletedAndUserId(completed, currentUser.getId())
                .stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosByPriority(Todo.Priority priority) {
        log.info("Fetching todos by priority: {}", priority);

        return todoRepository.findByPriority(priority)
                .stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> searchTodosByTitle(String title) {
        log.info("Searching todos by title: {}", title);

        return todoRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getOverdueTodos() {
        log.info("Fetching overdue todos");

        return todoRepository.findOverdueTodos(LocalDateTime.now())
                .stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoResponse> getTodosDueBetween(LocalDateTime start, LocalDateTime end) {
        log.info("Fetching todos due between {} and {}", start, end);

        return todoRepository.findByDueDateBetween(start, end)
                .stream()
                .map(TodoResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TodoResponse markAsCompleted(Long id) {
        log.info("Marking todo as completed with ID: {}", id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with ID: " + id));

        todo.setCompleted(true);
        Todo updatedTodo = todoRepository.save(todo);

        return TodoResponse.fromEntity(updatedTodo);
    }

    @Override
    public TodoResponse markAsIncomplete(Long id) {
        log.info("Marking todo as incomplete with ID: {}", id);

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with ID: " + id));

        todo.setCompleted(false);
        Todo updatedTodo = todoRepository.save(todo);

        return TodoResponse.fromEntity(updatedTodo);
    }

    @Override
    @Transactional(readOnly = true)
    public long getCompletedTodosCount() {
        return todoRepository.countByCompleted(true);
    }

    @Override
    @Transactional(readOnly = true)
    public long getPendingTodosCount() {
        return todoRepository.countByCompleted(false);
    }
}
