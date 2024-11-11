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

    public static UserDTO toDTO(User user)
    {
        UserDTO userDTO= UserDTO.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
        userDTO.setId(user.getUserId());
        return userDTO;
    }


}
