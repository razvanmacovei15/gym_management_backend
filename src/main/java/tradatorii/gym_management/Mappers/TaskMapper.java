package tradatorii.gym_management.Mappers;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.time.LocalDateTime;

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
}
