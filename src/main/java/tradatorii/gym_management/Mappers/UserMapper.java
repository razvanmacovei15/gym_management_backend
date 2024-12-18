package tradatorii.gym_management.Mappers;

import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User toEntity(UserDTO userDTO)
    {
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }

    public UserDTO toDTO(User user)
    {
        return UserDTO.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }
    public List<UserDTO> toDTOList(List<User> users)
    {
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users)
        {
            UserDTO userDTO = toDTO(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
