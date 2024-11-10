package tradatorii.gym_management.Mappers;

import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;

@Component
public class UserMapper {
    public User toEntity(UserDTO userDTO)
    {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(Role.valueOf(userDTO.getRole()))
                .build();
    }
}
