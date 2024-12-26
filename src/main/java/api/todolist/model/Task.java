package api.todolist.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonBackReference;

import api.todolist.converter.PriorityConverter;
import api.todolist.converter.StatusConverter;

@Entity
@Table(name = "task", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id"),
})
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private Category category;

    @ManyToOne
    @JsonBackReference
    private Users user;

    @NotNull
    @Size(max = 255)
    @Column(nullable = false, length = 255)
    private String title;

    @Size(max = 10000)
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Convert(converter = PriorityConverter.class)
    @Column(nullable = false, length = 6)
    private Priority priority = Priority.MEDIUM;

    @Column
    private LocalDateTime deadline;

    @NotNull
    @Convert(converter = StatusConverter.class)
    @Column(nullable = false, length = 12)
    private Status status = Status.PENDING;

    @NotNull
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public enum Priority {
        LOW, MEDIUM, HIGH;

        public static Priority fromString(String value) {
            for (Priority priority : Priority.values()) {
                if (priority.name().equalsIgnoreCase(value)) {
                    return priority;
                }
            }
            throw new IllegalArgumentException("Invalid Priority value: " + value);
        }
    }

    public enum Status {
        PENDING, IN_PROGRESS, COMPLETED;

        public static Status fromString(String value) {
            for (Status status : Status.values()) {
                if (status.name().equalsIgnoreCase(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Invalid Status value: " + value);
        }
    }

    public Task() {
        // Default constructor
    }

    public Task(Category category, Users user, String title, String description, Priority priority,
            LocalDateTime deadline,
            Status status) {
        this.category = category;
        this.user = user;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
