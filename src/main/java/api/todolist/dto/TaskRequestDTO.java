package api.todolist.dto;

import java.time.LocalDateTime;

public class TaskRequestDTO {

    private String title;
    private String description;
    private String priority; // Bisa diganti dengan Enum
    private String status; // Bisa diganti dengan Enum
    private LocalDateTime deadline;
    private Long categoryId; // Menggunakan ID kategori
    private Long userId; // Menggunakan ID pengguna

    public TaskRequestDTO() {
    }

    public TaskRequestDTO(String title, String description, String priority, String status, LocalDateTime deadline,
            Long categoryId, Long userId) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.deadline = deadline;
        this.categoryId = categoryId;
        this.userId = userId;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
