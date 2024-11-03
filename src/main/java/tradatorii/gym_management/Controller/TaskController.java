package tradatorii.gym_management.Controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Wrapper.TaskWrapper;

@RestController
@RequestMapping("/task")
public class TaskController {




    @PostMapping("/create")
    public TaskDTO createTask(@RequestBody TaskWrapper taskWrp)
    {

        TaskDTO taskDTO= taskWrp.getTaskDTO();
        Task task= TaskMapper.toEntity(taskWrp);
        return taskDTO;
    }


}
