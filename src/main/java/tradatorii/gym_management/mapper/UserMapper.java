package tradatorii.gym_management.mapper;

import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import tradatorii.gym_management.dto.UserDto;
import tradatorii.gym_management.entity.User;

@Component
public class UserMapper {
    public User mapFrom(UserDto userDto) {
        return User.builder()
                .name(userDto.getName())
                .build();
    }
    public UserDto mapTo(User user) {
        return UserDto.builder()
                .name(user.getName())
                .build();
    }
}
