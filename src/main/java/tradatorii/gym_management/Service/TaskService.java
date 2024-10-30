package tradatorii.gym_management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Repo.TaskRepo;

import java.util.List;


@Service
public class TaskService {

    private final TaskRepo taskRepo;


    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
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
