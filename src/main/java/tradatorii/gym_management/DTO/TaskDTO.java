package tradatorii.gym_management.DTO;

import lombok.*;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TaskDTO {

    private Long taskId;
    private String title;
    private Category category;
    private String description;
    private String deadline;
    private String priority;
    private Status status;
    private List<UserDTO> users;
    private List<GymDTO> gyms;
}
