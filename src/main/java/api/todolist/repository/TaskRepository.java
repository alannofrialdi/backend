package api.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api.todolist.model.Task;
import api.todolist.model.Category;
import api.todolist.model.Users;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Cari berdasarkan Category
    List<Task> findByCategory(Category category);

    // Cari berdasarkan Category dan User
    // List<Task> findByCategoryAndUser(Category category, Users user);

    // Contoh tambahan query menggunakan JPQL jika diperlukan

    List<Task> findTasksByCategoryAndCategory_User(Category category, Users user);
}
