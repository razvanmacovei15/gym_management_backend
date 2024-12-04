package tradatorii.gym_management.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@AllArgsConstructor

public class LoginDTO {
    private String email;
    private String password;
}
