package tradatorii.gym_management.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.LoginDTO;
import tradatorii.gym_management.DTO.RegisterUserDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.Service.implementations.UserService;
import tradatorii.gym_management.minio.MinioService;
import tradatorii.gym_management.security.LoginResponse;
import tradatorii.gym_management.security.services.AuthenticationService;
import tradatorii.gym_management.security.services.JwtService;
@RequestMapping("/auth")
@RestController

public class AuthenticationController {
    private final UserMapper userMapper;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final MinioService minioService;

    public AuthenticationController(UserMapper userMapper, JwtService jwtService, AuthenticationService authenticationService, UserService userService, MinioService minioService) {
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.minioService = minioService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);

        String userBucket = userService.createUserBucket(registeredUser); //TODO maybe move this into the minio service
        registeredUser.setUserBucket(userBucket);
        userService.setDefaultProfilePhoto(registeredUser);

        User savedUser = userService.save(registeredUser);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDTO loginUserDto) {

        System.out.println("🔍 Login request received: " + loginUserDto.getEmail());

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        if (authenticatedUser == null) {
            System.out.println("❌ Authentication failed: User not found or incorrect credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        System.out.println("✅ User authenticated: " + authenticatedUser.getUsername());

        String jwtToken = jwtService.generateToken(authenticatedUser);

        if (jwtToken == null || jwtToken.isEmpty()) {
            System.out.println("❌ JWT Token generation failed!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        System.out.println("✅ JWT Token generated: " + jwtToken);

        String pUrl = userService.generatePreSignedUrl(authenticatedUser);
        System.out.println("✅ PreSigned URL generated: " + pUrl);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(jwtToken)
                .expiresIn(jwtService.getExpirationTime())
                .user(userMapper.toDTO(authenticatedUser))
                .preSignedUrl(pUrl)
                .build();

        System.out.println("✅ Login response successfully created!");

        return ResponseEntity.ok(loginResponse);
    }


    @GetMapping("/me")
    public ResponseEntity<LoginResponse> verifyToken(
            @RequestHeader("Authorization") String authorizationHeader,
            @AuthenticationPrincipal User user) {

        // Extract token from "Bearer <token>" format
        String token = authorizationHeader.startsWith("Bearer ")
                ? authorizationHeader.substring(7)
                : authorizationHeader;

        // Check if the token is valid for the current user
        boolean isTokenValid = jwtService.isTokenValid(token, user);

        if (!isTokenValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String pUrl = userService.generatePreSignedUrl(user);

        // Construct the response
        LoginResponse loginResponse = LoginResponse.builder()
                .user(userMapper.toDTO(user))
                .token(token)
                .preSignedUrl(pUrl)
                .build();

        return ResponseEntity.ok(loginResponse);
    }
}
