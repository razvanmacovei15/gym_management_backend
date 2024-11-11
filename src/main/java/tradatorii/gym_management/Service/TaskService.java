package tradatorii.gym_management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Repo.TaskRepo;

import java.util.ArrayList;
import java.util.List;


@Service
public class TaskService implements TaskServiceInterface {


    private final TaskRepo taskRepo;


    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }

    @Override
    public Task save(Task task) {
        return taskRepo.save(task);
    }

    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepo.findAll();
        List<TaskDTO> taskDTOS = new ArrayList<>();
        for(Task task: tasks){
            TaskDTO taskDTO = TaskMapper.mapTo(task);
            taskDTOS.add(taskDTO);
        }

        return taskDTOS;
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





}
