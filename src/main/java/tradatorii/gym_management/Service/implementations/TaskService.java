package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Repo.TaskRepo;
import tradatorii.gym_management.Service.TaskServiceInterface;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService implements TaskServiceInterface {

    private final TaskRepo taskRepo;

    @Override
    public Task save(Task task) {
        return taskRepo.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task createTask(Task task) {
        return taskRepo.save(task);
    }

    public Long deleteTask(Long id) {
        taskRepo.deleteById(id);
        return id;
    }

    public Status updateStatus(Long id, Status status) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        taskRepo.save(task);
        return status;
    }

    public Task updateTask(Long id, Task task) {
        return taskRepo.findById(id)
                .map(t -> {
                    t.setCategory(task.getCategory());
                    t.setDescription(task.getDescription());
                    t.setDeadline(task.getDeadline());
                    t.setPriority(task.getPriority());
                    t.setStatus(task.getStatus());
                    t.setUsersResponsibleForTask(task.getUsersResponsibleForTask());
                    t.setGymSet(task.getGymSet());
                    t.setSubcategory(task.getSubcategory());
                    return taskRepo.save(t);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }



}
