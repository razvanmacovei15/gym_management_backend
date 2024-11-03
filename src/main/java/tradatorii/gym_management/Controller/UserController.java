package tradatorii.gym_management.Controller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.Service.implementations.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
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
    public List<User> getAllUsers()
    {
        return this.userService.getAllUsers();
    }
}

