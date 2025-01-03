package api.todolist.service;

import api.todolist.dto.TaskRequestDTO;
import api.todolist.dto.TaskResponseDTO;
import api.todolist.exception.CategoryUserMismatchException;
import api.todolist.mapper.TaskMapper;
import api.todolist.model.Category;
import api.todolist.model.Task;
import api.todolist.model.Users;
import api.todolist.repository.CategoryRepository;
import api.todolist.repository.TaskRepository;
import api.todolist.repository.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

        private final TaskRepository taskRepository;
        private final TaskMapper taskMapper;
        private final CategoryRepository categoryRepository;
        private final UsersRepository usersRepository;

        public TaskService(TaskRepository taskRepository, TaskMapper taskMapper,
                        CategoryRepository categoryRepository, UsersRepository usersRepository) {
                this.taskRepository = taskRepository;
                this.taskMapper = taskMapper;
                this.categoryRepository = categoryRepository;
                this.usersRepository = usersRepository;
        }

        public List<Task> filterTasksByUserAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
                return taskRepository.findByUserAndCreatedAtBetween(userId, startDate, endDate);
        }

        public List<TaskResponseDTO> getTasksByCategory(Long categoryId) {
                Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Category with ID " + categoryId + " not found"));

                return taskRepository.findByCategory(category)
                                .stream()
                                .map(taskMapper::toDTO)
                                .collect(Collectors.toList());
        }

        public TaskResponseDTO createTask(TaskRequestDTO requestDTO) {
                // Cari user berdasarkan ID
                Users user = usersRepository.findById(requestDTO.getUserId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "User with ID " + requestDTO.getUserId() + " not found"));

                // Cari category berdasarkan ID
                Category category = categoryRepository.findById(requestDTO.getCategoryId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Category with ID " + requestDTO.getCategoryId() + " not found"));

                // Validasi apakah category milik user
                if (!category.getUser().getId().equals(user.getId())) {
                        throw new CategoryUserMismatchException(
                                        "Category with ID " + requestDTO.getCategoryId() +
                                                        " does not belong to User with ID " + requestDTO.getUserId());
                }

                // Jika validasi lolos, buat Task baru
                Task task = taskMapper.toEntity(requestDTO, category, user);
                Task savedTask = taskRepository.save(task);

                return taskMapper.toDTO(savedTask);
        }

        public TaskResponseDTO getTaskById(Long id) {
                Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));
                return taskMapper.toDTO(task);
        }

        public TaskResponseDTO updateTask(Long id, TaskRequestDTO requestDTO) {
                Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));

                Category category = categoryRepository.findById(requestDTO.getCategoryId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Category with ID " + requestDTO.getCategoryId() + " not found"));

                task.setTitle(requestDTO.getTitle());
                task.setDescription(requestDTO.getDescription());
                task.setDeadline(requestDTO.getDeadline());
                task.setPriority(Task.Priority.fromString(requestDTO.getPriority()));
                task.setStatus(Task.Status.fromString(requestDTO.getStatus()));

                task.setCategory(category);

                Task updatedTask = taskRepository.save(task);
                return taskMapper.toDTO(updatedTask);
        }

        public void deleteTask(Long id) {
                Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));
                taskRepository.delete(task);
        }
}
