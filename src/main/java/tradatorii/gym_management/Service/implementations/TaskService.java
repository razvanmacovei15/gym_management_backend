package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Repo.TaskRepo;
import tradatorii.gym_management.Service.TaskServiceInterface;

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





}
