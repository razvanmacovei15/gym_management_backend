package tradatorii.gym_management.Mappers;

import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;

public class UserMapper {

    public static User toEntity(UserDTO userDTO)
    {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(Role.valueOf(userDTO.getRole()))
                .build();
    }
}
