package api.todolist.repository;

import api.todolist.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    // Menemukan semua task berdasarkan user ID
    List<Task> findByUserId(Long userId);
}
