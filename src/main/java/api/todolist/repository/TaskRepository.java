package api.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import api.todolist.model.Task;
import api.todolist.model.Users;
import api.todolist.model.Category;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCategory(Category category);

    List<Task> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT t FROM Task t JOIN t.category c WHERE c.user.id = :userId AND t.createdAt BETWEEN :startDate AND :endDate")
    List<Task> findByUserAndCreatedAtBetween(@Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    List<Task> findByCategoryAndCategory_User(Category category, Users user);
}
