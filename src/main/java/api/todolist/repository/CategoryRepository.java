package api.todolist.repository;

import api.todolist.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByCategory(String categoryName);

    boolean existsByUser_UsernameAndCategory(String username, String category);

}
