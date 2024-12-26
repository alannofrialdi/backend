package api.todolist.mapper;

import api.todolist.dto.TaskRequestDTO;
import api.todolist.dto.TaskResponseDTO;
import api.todolist.model.Task;
import api.todolist.model.Category;
import api.todolist.model.Users;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public Task toEntity(TaskRequestDTO dto, Category category, Users user) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setPriority(Task.Priority.fromString(dto.getPriority())); // Ensure fromString handles null
        task.setStatus(Task.Status.fromString(dto.getStatus())); // Ensure fromString handles null
        task.setDeadline(dto.getDeadline());
        task.setCategory(category);
        task.setUser(user);
        return task;
    }

    public TaskResponseDTO toDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setPriority(task.getPriority() != null ? task.getPriority().name() : null);
        dto.setStatus(task.getStatus() != null ? task.getStatus().name() : null);
        dto.setDeadline(task.getDeadline());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setUpdatedAt(task.getUpdatedAt());
        dto.setCategoryId(task.getCategory() != null ? task.getCategory().getId() : null);
        dto.setCategoryName(task.getCategory() != null ? task.getCategory().getCategory() : null);
        dto.setUserId(task.getUser() != null ? task.getUser().getId() : null);
        dto.setUsername(task.getUser() != null ? task.getUser().getUsername() : null);
        return dto;
    }
}
