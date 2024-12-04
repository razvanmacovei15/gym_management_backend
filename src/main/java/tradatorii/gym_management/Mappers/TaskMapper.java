package tradatorii.gym_management.Mappers;
import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskMapper {
    public Task toEntity(TaskDTO taskDTO){
        return Task.builder()
                .category(taskDTO.getCategory())
                .description(taskDTO.getDescription())
                .deadline(taskDTO.getDeadline())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .build();
    }


    public TaskDTO mapFrom(Task task){
        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .category(task.getCategory())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .priority(task.getPriority())
                .subcategory(task.getSubcategory())
                .status(task.getStatus())
                .build();
    }

    public List<TaskDTO> taskDTOList(Set<Task> tasks){
        List<TaskDTO> taskList = tasks.stream().map(this::mapFrom).collect(Collectors.toList());
        return taskList;
    }
}
