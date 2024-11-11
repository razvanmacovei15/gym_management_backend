package tradatorii.gym_management.DTO;


import lombok.*;
import tradatorii.gym_management.Enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

public class StatusChangeDTO {

    private Long id;
    private Status status;
}
