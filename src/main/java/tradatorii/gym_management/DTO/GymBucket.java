package tradatorii.gym_management.DTO;


import lombok.*;
import tradatorii.gym_management.Entity.Task;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymBucket {

    Long gymId;
    String gymName;
    Integer totalTasks;
    Integer completedTasks;
    Integer toDoTasks;
    Integer backlogTasks;
    Integer inProgressTasks;
    Integer cancelledTasks;
    List<TaskDTO> tasks;


}
