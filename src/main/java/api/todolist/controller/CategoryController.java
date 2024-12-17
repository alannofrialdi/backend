package api.todolist.controller;

import api.todolist.model.Category;
import api.todolist.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create Category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestParam String username, @RequestBody Category category) {
        Category newCategory = categoryService.addCategory(username, category);
        return ResponseEntity.ok(newCategory);
    }

    // Read Categories by User
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(@RequestParam String username) {
        List<Category> categories = categoryService.getUserCategories(username);
        return ResponseEntity.ok(categories);
    }

    // Update Category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @RequestParam String username,
            @PathVariable Long id,
            @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(username, id, categoryDetails);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @RequestParam String username,
            @PathVariable Long id) {
        categoryService.deleteCategory(username, id);
        return ResponseEntity.noContent().build();
    }
}
