package com.example.todo_list_api.service;

import com.example.todo_list_api.dto.request.TodoCreateRequest;
import com.example.todo_list_api.dto.request.TodoUpdateRequest;
import com.example.todo_list_api.dto.response.TodoResponse;
import com.example.todo_list_api.model.Todo;

import java.time.LocalDateTime;
import java.util.List;

public interface TodoService {

    TodoResponse createTodo(TodoCreateRequest request);

    TodoResponse getTodoById(Long id);

    List<TodoResponse> getAllTodos();

    TodoResponse updateTodo(Long id, TodoUpdateRequest request);

    void deleteTodo(Long id);

    List<TodoResponse> getTodosByStatus(Boolean completed);

    List<TodoResponse> getTodosByPriority(Todo.Priority priority);

    List<TodoResponse> searchTodosByTitle(String title);

    List<TodoResponse> getOverdueTodos();

    List<TodoResponse> getTodosDueBetween(LocalDateTime start, LocalDateTime end);

    TodoResponse markAsCompleted(Long id);

    TodoResponse markAsIncomplete(Long id);

    long getCompletedTodosCount();

    long getPendingTodosCount();
}
