package tradatorii.gym_management.DTO;

import lombok.*;

import java.util.Set;

@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class TaskRequestDTO {
    private TaskDTO taskDTO;
    private Set<UserDTO> users;
    private Set<GymDTO> gyms;
}
