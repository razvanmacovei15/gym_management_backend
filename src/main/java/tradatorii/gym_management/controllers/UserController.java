package tradatorii.gym_management.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.dto.UserDto;
import tradatorii.gym_management.entity.User;
import tradatorii.gym_management.mapper.UserMapper;
import tradatorii.gym_management.services.UserService;

@RestController
@RequestMapping(path = "/api/users")
@AllArgsConstructor

public class UserController {
    private final UserService userService;
    private UserMapper userMapper;

    @PostMapping(path = "/addUser")
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapFrom(userDto);
        User savedUser = userService.save(user);
        return userMapper.mapTo(savedUser);
    }

    @GetMapping(path="/user")
    public UserDto getUser() {
        User user = userService.getUser(1L);
        return userMapper.mapTo(user);
    }
}
