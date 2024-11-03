package tradatorii.gym_management.Controller;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Service.TaskServiceInterface;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.util.Set;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskServiceInterface taskService;
    private TaskMapper taskMapper;


    @PostMapping("/create")
    public TaskDTO createTask(@RequestBody TaskWrapper taskWrp)
    {

        TaskDTO taskDTO= taskWrp.getTaskDTO();
        Set<User> userSet = taskWrp.getUsersResponsibleForTask();
        Set<Gym> gymSet = taskWrp.getGymSet();

        Task savedTask = taskService.save(taskMapper.mapFrom(taskDTO));
        savedTask.setUsersResponsibleForTask(userSet);
        savedTask.setGymSet(gymSet);

        return taskDTO;
    }


}
