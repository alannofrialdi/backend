package api.todolist.service;

import api.todolist.dto.CategoryDTO;
import api.todolist.mapper.CategoryMapper;
import api.todolist.model.Category;
import api.todolist.model.Users;
import api.todolist.repository.CategoryRepository;
import api.todolist.repository.UsersRepository;

import org.springframework.dao.DataIntegrityViolationException;
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

    public CategoryDTO addCategory(String username, Category category) {
        try {
            Users user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            category.setUser(user);
            category.setCreatedAt(LocalDateTime.now());
            category.setUpdatedAt(LocalDateTime.now());
            Category savedCategory = categoryRepository.save(category);

            return CategoryMapper.toDTO(savedCategory);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }

    public boolean doesCategoryExist(String username, String categoryName) {
        return categoryRepository.existsByUser_UsernameAndCategory(username, categoryName);
    }

    // Get Categories by User
    public List<CategoryDTO> getUserCategories(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found:" + username));

        return CategoryMapper.toDTOList(user.getCategories());
    }

    // Update Category
    public CategoryDTO updateCategory(String username, Long categoryId, Category categoryDetails) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Category category = categoryRepository.findById(categoryId)
                .filter(cat -> cat.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Category not found or not owned by user"));

        category.setCategory(categoryDetails.getCategory());
        category.setUpdatedAt(LocalDateTime.now());
        Category updatedCategory = categoryRepository.save(category);

        return CategoryMapper.toDTO(updatedCategory);
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
