package api.todolist.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CategoryDTO {
    private Long id;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TaskResponseDTO> tasks;

    public CategoryDTO() {
    }

    public CategoryDTO(Long id, String category, LocalDateTime createdAt, LocalDateTime updatedAt,
            List<TaskResponseDTO> tasks) {
        this.id = id;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<TaskResponseDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponseDTO> tasks) {
        this.tasks = tasks;
    }
}
