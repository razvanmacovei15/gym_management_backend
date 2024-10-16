package tradatorii.gym_management.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.entity.User;
import tradatorii.gym_management.services.UserService;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor

public class UserController {
    private UserService userService;

    @PostMapping(path = "/addUser")
    public void createUser(@RequestBody User user) {
        this.userService.save(user);
    }
}
