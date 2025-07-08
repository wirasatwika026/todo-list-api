// package com.example.todo_list_api.controller;

// import com.example.todo_list_api.dto.request.TodoCreateRequest;
// import com.example.todo_list_api.model.Todo;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.web.client.TestRestTemplate;
// import org.springframework.boot.test.web.server.LocalServerPort;
// import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.transaction.annotation.Transactional;

// import java.util.Map;

// import static org.junit.jupiter.api.Assertions.*;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// @ActiveProfiles("test")
// @Transactional
// class TodoControllerIntegrationTest {

// @LocalServerPort
// private int port;

// @Autowired
// private TestRestTemplate restTemplate;

// private String createURLWithPort(String uri) {
// return "http://localhost:" + port + uri;
// }

// @Test
// void createTodo_ShouldReturnCreatedTodo() {
// TodoCreateRequest request = new TodoCreateRequest();
// request.setTitle("Test Todo");
// request.setDescription("Test Description");
// request.setPriority(Todo.Priority.HIGH);

// HttpHeaders headers = new HttpHeaders();
// headers.setContentType(MediaType.APPLICATION_JSON);
// HttpEntity<TodoCreateRequest> entity = new HttpEntity<>(request, headers);

// @SuppressWarnings("rawtypes")
// ResponseEntity<Map> response = restTemplate.postForEntity(
// createURLWithPort("/api/todos"), entity, Map.class);

// assertEquals(HttpStatus.CREATED, response.getStatusCode());
// assertNotNull(response.getBody());
// assertTrue((Boolean) response.getBody().get("success"));
// }

// @Test
// void getAllTodos_ShouldReturnOkResponse() {
// @SuppressWarnings("rawtypes")
// ResponseEntity<Map> response = restTemplate.getForEntity(
// createURLWithPort("/api/todos"), Map.class);

// assertEquals(HttpStatus.OK, response.getStatusCode());
// assertNotNull(response.getBody());
// assertTrue((Boolean) response.getBody().get("success"));
// }

// @Test
// void getTodoStatistics_ShouldReturnStatistics() {
// @SuppressWarnings("rawtypes")
// ResponseEntity<Map> response = restTemplate.getForEntity(
// createURLWithPort("/api/todos/statistics"), Map.class);

// assertEquals(HttpStatus.OK, response.getStatusCode());
// assertNotNull(response.getBody());
// assertTrue((Boolean) response.getBody().get("success"));

// @SuppressWarnings("unchecked")
// Map<String, Object> data = (Map<String, Object>)
// response.getBody().get("data");
// assertNotNull(data);
// assertTrue(data.containsKey("total"));
// assertTrue(data.containsKey("completed"));
// assertTrue(data.containsKey("pending"));
// }
// }
