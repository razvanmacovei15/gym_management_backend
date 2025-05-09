package tradatorii.gym_management.Mappers;

import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UserMapper {

    private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

    public User toEntity(UserDTO userDTO) {
        log.debug("Mapping UserDTO to User entity: {}", userDTO);
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }

    public UserDTO toDTO(User user) {
        log.debug("Mapping User entity to UserDTO: {}", user);
        return UserDTO.builder()
                .id(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public List<UserDTO> toDTOList(List<User> users) {
        log.debug("Mapping list of User entities to list of UserDTOs");
        List<UserDTO> userDTOS = new ArrayList<>();
        for (User user : users) {
            UserDTO userDTO = toDTO(user);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }
}
