package api.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import api.todolist.model.Task;
import api.todolist.model.Category;
import api.todolist.model.Users;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    // Cari berdasarkan Category
    List<Task> findByCategory(Category category);

    // Cari berdasarkan User
    List<Task> findByUser(Users user);

    // Cari berdasarkan Category dan User
    List<Task> findByCategoryAndUser(Category category, Users user);

    // Contoh tambahan query menggunakan JPQL jika diperlukan
    @Query("SELECT t FROM Task t WHERE t.category = :category AND t.user = :user")
    List<Task> findTasksByCategoryAndUser(@Param("category") Category category, @Param("user") Users user);
}
