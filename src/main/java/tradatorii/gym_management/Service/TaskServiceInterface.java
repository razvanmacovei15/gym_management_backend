package tradatorii.gym_management.Service;

import tradatorii.gym_management.Entity.Task;

import java.util.List;

public interface TaskServiceInterface {
    Task save(Task task);
    List<Task> getAllTasks();
}
