package tradatorii.gym_management.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.LoginDTO;
import tradatorii.gym_management.DTO.RegisterUserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.security.LoginResponse;
import tradatorii.gym_management.security.services.AuthenticationService;
import tradatorii.gym_management.security.services.JwtService;
@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:3000", // Replace with your frontend's URL
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")
public class AuthenticationController {
    private final UserMapper userMapper;

    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(UserMapper userMapper, JwtService jwtService, AuthenticationService authenticationService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDTO loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .user(userMapper.toDTO(authenticatedUser))
                .build();

        System.out.println(loginResponse.getUser().getRole());

        return ResponseEntity.ok(loginResponse);
    }
}
