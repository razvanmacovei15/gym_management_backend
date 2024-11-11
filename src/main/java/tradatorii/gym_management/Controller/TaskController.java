package tradatorii.gym_management.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.StatusChangeDTO;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Service.TaskService;
import tradatorii.gym_management.Service.TaskServiceInterface;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private TaskServiceInterface taskService;
   // private TaskMapper taskMapper;


    @Autowired
    public TaskController(TaskServiceInterface taskService)
    {
        this.taskService = taskService;
    }


    @PostMapping("/create")
    public TaskDTO createTask(@RequestBody TaskWrapper taskWrp)
    {

        TaskDTO taskDTO= taskWrp.getTaskDTO();
        Set<User> userSet = taskWrp.getUsersResponsibleForTask();
        Set<Gym> gymSet = taskWrp.getGymSet();

        Task savedTask = taskService.save(TaskMapper.mapFrom(taskDTO));
        savedTask.setUsersResponsibleForTask(userSet);
        savedTask.setGymSet(gymSet);

        return taskDTO;
    }

    @GetMapping("/all")
    public List<TaskDTO> getAllTasks()
    {
        return taskService.getAllTasks();
    }


    @PostMapping("/changeStatus")
    public ResponseEntity<Status> changeStatus(@RequestBody StatusChangeDTO statusChangeDTO)
    {
        Long id = statusChangeDTO.getId();
        Status status = statusChangeDTO.getStatus();
        taskService.updateStatus(id, status);
        return ResponseEntity.ok(status);

    }



}
