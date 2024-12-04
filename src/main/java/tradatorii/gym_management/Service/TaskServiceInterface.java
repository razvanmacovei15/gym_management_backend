package tradatorii.gym_management.Service;

import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;

import java.util.List;

public interface TaskServiceInterface {
    Task save(Task task);
    List<Task> getAllTasks();

    Task createTask(Task task);
    Long deleteTask(Long id);
    Status updateStatus(Long id, Status status);

    Task updateTask(Long id, Task task);

}
