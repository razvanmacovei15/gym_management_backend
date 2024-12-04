package tradatorii.gym_management.Controller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.Service.implementations.UserService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    public User createUser(@RequestBody UserDTO userDTO)
    {
        User user = userMapper.toEntity(userDTO);
        this.userService.save(user);
        return user;
    }


    @DeleteMapping
    public ResponseEntity<Long> deleteUser(@RequestParam Long id)
    {
        return ResponseEntity.ok(this.userService.delete(id));
    }

    @GetMapping
    public List<UserDTO> getAllUsers()
    {
        List<User> users = this.userService.getAllUsers();
        return userMapper.toDTOList(users);
    }


    @PatchMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestParam Long userId, @RequestBody UserDTO userDTO)
    {
        User user = userMapper.toEntity(userDTO);
        User updatedUser = this.userService.update(userId, user);
        UserDTO updatedUserDTO = userMapper.toDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);

    }

    @GetMapping("/createdTasks")
    public List<TaskDTO> getCreatedTasks(@RequestParam Long userId)
    {
        Set<Task> tasks = this.userService.getCreatedTasks(userId);
        return taskMapper.taskDTOList(tasks);

    }

}

