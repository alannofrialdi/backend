package api.todolist.mapper;

import api.todolist.dto.TaskDTO;
import api.todolist.model.Task;

public class TaskMapper {
    // Konversi dari Task ke TaskDTO
    public static TaskDTO toDTO(Task task) {
        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getPriority().name(),
                task.getStatus().name(),
                task.getDeadline(),
                task.getCategory().getCategory() // Ambil nama kategori
        );
    }

    // Konversi dari TaskDTO ke Task (dengan kategori)
    public static Task toEntity(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setPriority(Task.Priority.valueOf(taskDTO.getPriority()));
        task.setStatus(Task.Status.valueOf(taskDTO.getStatus()));
        task.setDeadline(taskDTO.getDeadline());
        // Kategori akan ditangani di Service (berdasarkan categoryName)
        return task;
    }
}
