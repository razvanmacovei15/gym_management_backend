package tradatorii.gym_management.DTO;

import lombok.*;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long taskId;
    private Category category;
    private String description;
    private String deadline;
    private String priority;
    private Status status;
    private Subcategory subcategory;


}
