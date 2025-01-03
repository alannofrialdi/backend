package api.todolist.controller;

import api.todolist.dto.TaskRequestDTO;
import api.todolist.dto.TaskResponseDTO;
import api.todolist.model.Task;
import api.todolist.service.TaskService;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDTO>> getTasksByCategory(@RequestParam Long categoryId) {
        List<TaskResponseDTO> tasks = taskService.getTasksByCategory(categoryId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/filterbydate")
    public ResponseEntity<List<Task>> filterTasksByUserAndDate(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        // if (startDate.isAfter(endDate)) {
        // throw new IllegalArgumentException("Start date must be before end date");
        // }

        List<Task> tasks = taskService.filterTasksByUserAndDate(userId, startDate, endDate);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO requestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(requestDTO);
        return ResponseEntity.ok(createdTask);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        TaskResponseDTO task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTask(@PathVariable Long id,
            @RequestBody TaskRequestDTO requestDTO) {
        Map<String, Object> response = new HashMap<>();

        taskService.updateTask(id, requestDTO);
        response.put("status", "ok");
        response.put("message", "Updated task succesfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}
