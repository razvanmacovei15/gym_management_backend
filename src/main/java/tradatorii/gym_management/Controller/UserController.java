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

    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("Ramo te rog ia-mi un PopCola sa am la tine acasa cand mai vin!\n If Alex is reading, whats up brother? \n Lets see if this updates the docker while it is still running!");
    }
}
