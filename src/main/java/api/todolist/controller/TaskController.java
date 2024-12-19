package api.todolist.controller;

import api.todolist.dto.TaskDTO;
import api.todolist.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks(@RequestParam String username) {
        return ResponseEntity.ok(taskService.getTasksByUser(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@RequestParam String username, @PathVariable Long id) {
        return taskService.getTaskByIdAndUser(id, username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(
            @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.createTask(taskDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@RequestParam String username, @PathVariable Long id,
            @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, username, taskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@RequestParam String username, @PathVariable Long id) {
        taskService.deleteTask(id, username);
        return ResponseEntity.noContent().build();
    }
}
