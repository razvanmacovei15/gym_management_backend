package tradatorii.gym_management.Mappers;
import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.time.LocalDateTime;
@Component
public class TaskMapper {


    public static Task toEntity(TaskWrapper taskWrp)
    {
        TaskDTO taskDTO= taskWrp.getTaskDTO();
        Task task= Task.builder()
                .category(taskDTO.getCategory())
                .description(taskDTO.getDescription())
                .status(Status.PENDING)
                .deadline(String.valueOf(LocalDateTime.parse(taskDTO.getDeadline())))
                .createdAt(String.valueOf(LocalDateTime.now()))
                .usersResponsibleForTask(taskWrp.getUsersResponsibleForTask())
                .build();
        return task;
    }

    public static Task mapFrom(TaskDTO taskDTO){
        return Task.builder()
                .category(taskDTO.getCategory())
                .description(taskDTO.getDescription())
                .status(Status.PENDING)
                .deadline(taskDTO.getDeadline())
                .priority(taskDTO.getPriority())
                .build();
    }


    public static TaskDTO mapTo(Task task){
        TaskDTO taskDTO = TaskDTO.builder()
                .taskId(task.getTaskId())
                .category(task.getCategory())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .priority(task.getPriority())
                .build();
       // taskDTO.setStatus(task.getStatus());
        return taskDTO;
    }
}
