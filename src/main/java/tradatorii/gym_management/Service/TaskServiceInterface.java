package tradatorii.gym_management.Service;

import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.DTO.TaskRequestDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;

import java.util.List;

public interface TaskServiceInterface {
    Task save(Task task);
    List<Task> getAllTasks();
    List<Task> findAllOrderByCreatedAtDesc();
    Task createTask(Task task);
    Long deleteTask(Long id);
    Status updateStatus(Long id, Status status);
    Task updateTask(Long id, Task task);
    Task createNewTask(TaskRequestDTO taskDTO);
    Task createNewTask(TaskDTO taskDTO);
    Task getTaskById(Long id);
    String createTaskBucket(Task task);
    List<Task> getTasksByGym(Gym gym);
    List<Task> getTasksByManagerUserId(Long userId);
    void openTheGates();

}
