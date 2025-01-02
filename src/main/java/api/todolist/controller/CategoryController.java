package api.todolist.controller;

import api.todolist.dto.CategoryDTO;
import api.todolist.model.Category;
import api.todolist.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestParam String username, @RequestBody Category category) {
        try {
            // Periksa apakah kategori sudah ada untuk pengguna tertentu
            boolean isExist = categoryService.doesCategoryExist(username, category.getCategory());

            if (isExist) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "HttpStatus.CONFLICT");
                response.put("code", 409);
                response.put("message", "Category already exists");
                return ResponseEntity.status(200)
                        .body(response);
            }

            // Tambahkan kategori baru
            CategoryDTO newCategory = categoryService.addCategory(username, category);
            return ResponseEntity.ok(newCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create category: " + e.getMessage());
        }
    }

    // Get Categories by User
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCategories(@RequestParam String username) {
        List<CategoryDTO> categories = categoryService.getUserCategories(username);
        return ResponseEntity.ok(categories);
    }

    // Update Category
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @RequestParam String username,
            @PathVariable Long id,
            @RequestBody Category categoryDetails) {
        try {
            CategoryDTO updatedCategory = categoryService.updateCategory(username, id, categoryDetails);
            return ResponseEntity.ok(updatedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update category: " + e.getMessage());
        }
    }

    // Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(
            @RequestParam String username,
            @PathVariable Long id) {
        try {
            categoryService.deleteCategory(username, id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete category: " + e.getMessage());
        }
    }
}