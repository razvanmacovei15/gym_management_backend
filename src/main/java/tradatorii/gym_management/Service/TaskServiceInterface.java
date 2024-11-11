package tradatorii.gym_management.Service;

import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;

import java.util.List;

public interface TaskServiceInterface {
    Task save(Task task);

    Status updateStatus(Long id, Status status);

    List<TaskDTO> getAllTasks();
}
