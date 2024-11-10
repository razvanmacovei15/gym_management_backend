package tradatorii.gym_management.DTO;

import lombok.*;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class TaskDTO {

    private Long taskId;
    private Category category;
    private String description;
    private String deadline;
    private String priority;
    private Subcategory subcategory;
    private Status status;

}
