package tradatorii.gym_management.DTO;


import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GymStatistics {

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
