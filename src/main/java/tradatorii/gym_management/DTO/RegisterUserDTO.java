package tradatorii.gym_management.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tradatorii.gym_management.Enums.Role;

@Builder
@Setter
@Getter
@AllArgsConstructor

public class RegisterUserDTO {
    private String name;
    private String email;
    private String password;
    private Role role;


}
