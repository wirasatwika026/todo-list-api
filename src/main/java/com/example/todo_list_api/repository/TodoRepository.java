package com.example.todo_list_api.repository;

import com.example.todo_list_api.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    // Find todos by user
    List<Todo> findByUserId(Long userId);

    // Find todos by completion status
    List<Todo> findByCompleted(Boolean completed);

    // Find todos by completion status and user
    List<Todo> findByCompletedAndUserId(Boolean completed, Long userId);

    // Find todos by priority
    List<Todo> findByPriority(Todo.Priority priority);

    // Find todos by priority and user
    List<Todo> findByPriorityAndUserId(Todo.Priority priority, Long userId);

    // Find todos by title containing (case insensitive)
    List<Todo> findByTitleContainingIgnoreCase(String title);

    // Find todos by title containing and user
    List<Todo> findByTitleContainingIgnoreCaseAndUserId(String title, Long userId);

    // Find todos due before a certain date
    List<Todo> findByDueDateBefore(LocalDateTime dateTime);

    // Find todos due between dates
    List<Todo> findByDueDateBetween(LocalDateTime start, LocalDateTime end);

    // Find todos due between dates and user
    List<Todo> findByDueDateBetweenAndUserId(LocalDateTime start, LocalDateTime end, Long userId);

    // Find overdue todos (due date is before now and not completed)
    @Query("SELECT t FROM Todo t WHERE t.dueDate < :now AND t.completed = false")
    List<Todo> findOverdueTodos(@Param("now") LocalDateTime now);

    // Find overdue todos by user
    @Query("SELECT t FROM Todo t WHERE t.dueDate < :now AND t.completed = false AND t.user.id = :userId")
    List<Todo> findOverdueTodosByUserId(@Param("now") LocalDateTime now, @Param("userId") Long userId);

    // Count completed todos
    long countByCompleted(Boolean completed);

    // Count completed todos by user
    long countByCompletedAndUserId(Boolean completed, Long userId);
}
