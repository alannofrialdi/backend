package api.todolist.service;

import api.todolist.model.Category;
import api.todolist.model.Users;
import api.todolist.repository.CategoryRepository;
import api.todolist.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final UsersRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UsersRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Create Category
    public Category addCategory(String username, Category category) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        category.setUser(user);
        return categoryRepository.save(category);
    }

    // Read Categories by User
    public List<Category> getUserCategories(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        return user.getCategories();
    }

    // Update Category
    public Category updateCategory(String username, Long categoryId, Category categoryDetails) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Category category = categoryRepository.findById(categoryId)
                .filter(cat -> cat.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Category not found or not owned by user"));

        category.setCategory(categoryDetails.getCategory());
        category.setUpdatedAt(LocalDateTime.now());

        return categoryRepository.save(category);
    }

    // Delete Category
    public void deleteCategory(String username, Long categoryId) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Category category = categoryRepository.findById(categoryId)
                .filter(cat -> cat.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Category not found or not owned by user"));

        categoryRepository.delete(category);
    }
}
