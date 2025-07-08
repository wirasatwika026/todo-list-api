// package com.example.todo_list_api.service;

// import com.example.todo_list_api.dto.request.TodoCreateRequest;
// import com.example.todo_list_api.dto.request.TodoUpdateRequest;
// import com.example.todo_list_api.dto.response.TodoResponse;
// import com.example.todo_list_api.exception.ResourceNotFoundException;
// import com.example.todo_list_api.model.Todo;
// import com.example.todo_list_api.repository.TodoRepository;
// import com.example.todo_list_api.service.impl.TodoServiceImpl;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.time.LocalDateTime;
// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class TodoServiceTest {

// @Mock
// private TodoRepository todoRepository;

// @InjectMocks
// private TodoServiceImpl todoService;

// private Todo testTodo;
// private TodoCreateRequest createRequest;
// private TodoUpdateRequest updateRequest;

// @BeforeEach
// void setUp() {
// testTodo = new Todo();
// testTodo.setId(1L);
// testTodo.setTitle("Test Todo");
// testTodo.setDescription("Test Description");
// testTodo.setCompleted(false);
// testTodo.setPriority(Todo.Priority.MEDIUM);
// testTodo.setCreatedAt(LocalDateTime.now());
// testTodo.setUpdatedAt(LocalDateTime.now());

// createRequest = new TodoCreateRequest();
// createRequest.setTitle("New Todo");
// createRequest.setDescription("New Description");
// createRequest.setPriority(Todo.Priority.HIGH);

// updateRequest = new TodoUpdateRequest();
// updateRequest.setTitle("Updated Todo");
// updateRequest.setDescription("Updated Description");
// updateRequest.setCompleted(true);
// }

// @Test
// void createTodo_ShouldReturnTodoResponse() {
// // Given
// when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);

// // When
// TodoResponse result = todoService.createTodo(createRequest);

// // Then
// assertNotNull(result);
// assertEquals(testTodo.getId(), result.getId());
// assertEquals(testTodo.getTitle(), result.getTitle());
// verify(todoRepository, times(1)).save(any(Todo.class));
// }

// @Test
// void getTodoById_WhenTodoExists_ShouldReturnTodoResponse() {
// // Given
// when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));

// // When
// TodoResponse result = todoService.getTodoById(1L);

// // Then
// assertNotNull(result);
// assertEquals(testTodo.getId(), result.getId());
// assertEquals(testTodo.getTitle(), result.getTitle());
// verify(todoRepository, times(1)).findById(1L);
// }

// @Test
// void getTodoById_WhenTodoNotExists_ShouldThrowException() {
// // Given
// when(todoRepository.findById(1L)).thenReturn(Optional.empty());

// // When & Then
// assertThrows(ResourceNotFoundException.class, () ->
// todoService.getTodoById(1L));
// verify(todoRepository, times(1)).findById(1L);
// }

// @Test
// void getAllTodos_ShouldReturnListOfTodoResponse() {
// // Given
// List<Todo> todos = Arrays.asList(testTodo);
// when(todoRepository.findAll()).thenReturn(todos);

// // When
// List<TodoResponse> result = todoService.getAllTodos();

// // Then
// assertNotNull(result);
// assertEquals(1, result.size());
// assertEquals(testTodo.getId(), result.get(0).getId());
// verify(todoRepository, times(1)).findAll();
// }

// @Test
// void updateTodo_WhenTodoExists_ShouldReturnUpdatedTodoResponse() {
// // Given
// when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));
// when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);

// // When
// TodoResponse result = todoService.updateTodo(1L, updateRequest);

// // Then
// assertNotNull(result);
// verify(todoRepository, times(1)).findById(1L);
// verify(todoRepository, times(1)).save(any(Todo.class));
// }

// @Test
// void deleteTodo_WhenTodoExists_ShouldDeleteSuccessfully() {
// // Given
// when(todoRepository.existsById(1L)).thenReturn(true);

// // When
// todoService.deleteTodo(1L);

// // Then
// verify(todoRepository, times(1)).existsById(1L);
// verify(todoRepository, times(1)).deleteById(1L);
// }

// @Test
// void deleteTodo_WhenTodoNotExists_ShouldThrowException() {
// // Given
// when(todoRepository.existsById(1L)).thenReturn(false);

// // When & Then
// assertThrows(ResourceNotFoundException.class, () ->
// todoService.deleteTodo(1L));
// verify(todoRepository, times(1)).existsById(1L);
// verify(todoRepository, never()).deleteById(anyLong());
// }

// @Test
// void markAsCompleted_WhenTodoExists_ShouldMarkAsCompleted() {
// // Given
// when(todoRepository.findById(1L)).thenReturn(Optional.of(testTodo));
// when(todoRepository.save(any(Todo.class))).thenReturn(testTodo);

// // When
// TodoResponse result = todoService.markAsCompleted(1L);

// // Then
// assertNotNull(result);
// verify(todoRepository, times(1)).findById(1L);
// verify(todoRepository, times(1)).save(any(Todo.class));
// }

// @Test
// void getTodosByStatus_ShouldReturnFilteredTodos() {
// // Given
// List<Todo> completedTodos = Arrays.asList(testTodo);
// when(todoRepository.findByCompleted(true)).thenReturn(completedTodos);

// // When
// List<TodoResponse> result = todoService.getTodosByStatus(true);

// // Then
// assertNotNull(result);
// assertEquals(1, result.size());
// verify(todoRepository, times(1)).findByCompleted(true);
// }
// }
