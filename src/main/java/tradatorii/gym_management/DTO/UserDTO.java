package tradatorii.gym_management.DTO;

import lombok.*;
import tradatorii.gym_management.Enums.Role;

import java.util.Objects;
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String photo;
}
