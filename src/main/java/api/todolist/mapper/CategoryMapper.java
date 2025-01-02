package api.todolist.mapper;

import api.todolist.dto.CategoryDTO;
import api.todolist.dto.TaskResponseDTO;
import api.todolist.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
        // Konversi dari Category ke CategoryDTO
        public static CategoryDTO toCategoryDTO(Category category) {
                return new CategoryDTO(
                                category.getId(),
                                category.getCategory(),
                                category.getCreatedAt(),
                                category.getUpdatedAt(),
                                category.getTasks().stream()
                                                .map(task -> new TaskResponseDTO(
                                                                task.getId(),
                                                                task.getTitle(),
                                                                task.getDescription(),
                                                                task.getPriority().name(),
                                                                task.getStatus().name(),
                                                                task.getDeadline(),
                                                                task.getCreatedAt(),
                                                                task.getUpdatedAt(),
                                                                task.getCategory().getId(),
                                                                task.getCategory().getCategory()))
                                                .collect(Collectors.toList()));
        }

        // Konversi List<Category> ke List<CategoryDTO>
        public static List<CategoryDTO> toDTOList(List<Category> categories) {
                return categories.stream()
                                .map(CategoryMapper::toCategoryDTO) // Ganti dengan nama metode yang benar
                                .collect(Collectors.toList());
        }
}
