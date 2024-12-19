package api.todolist.mapper;

import api.todolist.dto.CategoryDTO;
import api.todolist.dto.TaskDTO;
import api.todolist.model.Category;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {
        // Konversi dari Category ke CategoryDTO
        public static CategoryDTO toDTO(Category category) {
                return new CategoryDTO(
                                category.getId(),
                                category.getCategory(),
                                category.getCreatedAt(),
                                category.getUpdatedAt(),
                                category.getTasks()
                                                .stream()
                                                .map(task -> new TaskDTO(
                                                                task.getId(),
                                                                task.getTitle(),
                                                                task.getDescription(),
                                                                task.getPriority().name(),
                                                                task.getStatus().name(),
                                                                task.getDeadline(),
                                                                task.getCategory().getCategory()))
                                                .collect(Collectors.toList()));
        }

        // Konversi List<Category> ke List<CategoryDTO>
        public static List<CategoryDTO> toDTOList(List<Category> categories) {
                return categories.stream().map(CategoryMapper::toDTO).collect(Collectors.toList());
        }
}
