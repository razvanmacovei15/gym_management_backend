package tradatorii.gym_management.Mappers;

import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;

public class UserMapper {

    public static User toEntity(UserDTO userDTO)
    {
        String name = userDTO.getName();
        String email = userDTO.getEmail();
        Role role = Role.valueOf(userDTO.getRole());
        User user = new User(name,email,role);
        return user;
    }
}
