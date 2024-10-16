package tradatorii.gym_management.Mappers;

import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;

public class UserMapper {

    public static User toEntity(UserDTO userDTO)
    {
        String name = userDTO.getName();
        String email = userDTO.getEmail();
        User user = new User(name,email);
        return user;
    }
}
