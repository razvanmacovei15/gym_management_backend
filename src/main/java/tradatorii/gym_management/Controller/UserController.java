package tradatorii.gym_management.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.Service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService service)
    {
        this.userService = service;
    }

    @PostMapping
    public User createUser(@RequestBody UserDTO userDTO)
    {
        User user = UserMapper.toEntity(userDTO);
        this.userService.createUser(user);
        return user;
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteUser(@RequestParam Long id)
    {
        return ResponseEntity.ok(this.userService.deleteUser(id));
    }

    @GetMapping
    public List<User> getAllUsers()
    {
        return this.userService.getAllUsers();
    }
}
