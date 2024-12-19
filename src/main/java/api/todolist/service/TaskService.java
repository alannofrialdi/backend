package api.todolist.service;

import api.todolist.dto.TaskDTO;
import api.todolist.mapper.TaskMapper;
import api.todolist.model.Task;
import api.todolist.model.Category;
import api.todolist.repository.TaskRepository;
import api.todolist.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskService(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<TaskDTO> getTasksByUser(String username) {
        return taskRepository.findByCategoryUserUsername(username) // Query by user username
                .stream()
                .map(TaskMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(TaskMapper::toDTO);
    }

    public Optional<TaskDTO> getTaskByIdAndUser(Long id, String username) {
        return taskRepository.findByIdAndCategory_User_Username(id, username)
                .map(TaskMapper::toDTO);
    }

    public TaskDTO createTask(TaskDTO taskDTO) {
        Category category = categoryRepository.findByCategory(taskDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException(taskDTO.getCategoryName() + " Not Found"));

        System.out.println(taskDTO.getCategoryName() + " Not Found SOUT");
        Task task = TaskMapper.toEntity(taskDTO);
        task.setCategory(category);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public TaskDTO updateTask(Long id, String username, TaskDTO updatedTaskDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        // Validasi apakah task milik user yang benar
        if (!task.getCategory().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Task does not belong to user");
        }

        Category category = categoryRepository.findByCategory(updatedTaskDTO.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Update task
        task.setTitle(updatedTaskDTO.getTitle());
        task.setDescription(updatedTaskDTO.getDescription());
        task.setPriority(Task.Priority.valueOf(updatedTaskDTO.getPriority()));
        task.setStatus(Task.Status.valueOf(updatedTaskDTO.getStatus()));
        task.setDeadline(updatedTaskDTO.getDeadline());
        task.setCategory(category);

        return TaskMapper.toDTO(taskRepository.save(task));
    }

    public void deleteTask(Long id, String username) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getCategory().getUser().getUsername().equals(username)) {
            throw new RuntimeException("Task does not belong to user");
        }

        taskRepository.deleteById(id);
    }
}
