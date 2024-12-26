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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

        public List<TaskResponseDTO> getTasksByCategoryAndUser(Long categoryId, Long userId) {
                Category category = categoryRepository.findById(categoryId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Category with ID " + categoryId + " not found"));
                Users user = usersRepository.findById(userId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "User with ID " + userId + " not found"));

                return taskRepository.findByCategoryAndUser(category, user)
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

                Users user = usersRepository.findById(requestDTO.getUserId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "User with ID " + requestDTO.getUserId() + " not found"));

                task.setTitle(requestDTO.getTitle());
                task.setDescription(requestDTO.getDescription());
                task.setDeadline(requestDTO.getDeadline());
                task.setCategory(category);
                task.setUser(user);

                Task updatedTask = taskRepository.save(task);
                return taskMapper.toDTO(updatedTask);
        }

        public void deleteTask(Long id) {
                Task task = taskRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException("Task with ID " + id + " not found"));
                taskRepository.delete(task);
        }
}
