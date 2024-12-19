package api.todolist.repository;

import api.todolist.model.Task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryUserUsername(String username); // Ambil tugas berdasarkan username pengguna

    Optional<Task> findByIdAndCategory_User_Username(Long taskId, String username);

    // Optional<Task> getTaskByIdAndUser(Long taskId, String username); // Query
    // untuk mendapatkan task berdasarkan ID dan
    // // username pengguna
}
