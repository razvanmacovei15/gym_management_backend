package tradatorii.gym_management.security.services;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.DTO.LoginDTO;
import tradatorii.gym_management.DTO.RegisterUserDTO;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Repo.UserRepo;

@Service
public class AuthenticationService {
    // Repository for interacting with the User entity in the database
    private final UserRepo userRepository;
    // Password encoder for securely storing and validating passwords
    private final PasswordEncoder passwordEncoder;
    // Manages authentication processes, using configured authentication providers
    private final AuthenticationManager authenticationManager;
    // Constructor for dependency injection of UserRepo, AuthenticationManager, and PasswordEncoder
    public AuthenticationService(
            UserRepo userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Handles user registration by creating a new User entity.
     * The password is securely hashed before saving to the database.
     *
     * @param input Data Transfer Object containing registration details (email, username, password)
     * @return the saved User entity after registration
     */
    public User signup(RegisterUserDTO input) {
        // Creates a new User entity with the provided email, username, and encoded password
        User user = User.builder()
                .email(input.getEmail())
                .name(input.getName())
                .role(input.getRole())
                .password(passwordEncoder.encode(input.getPassword())) // Encrypts the password before saving
                .build();
        // Saves the new user to the repository (database) and returns the saved user entity
        return userRepository.save(user);
    }

    /**
     * Authenticates a user by checking the provided credentials against stored user data.
     * Uses AuthenticationManager to validate login credentials.
     *
     * @param input Data Transfer Object containing login details (email and password)
     * @return the authenticated User entity if login is successful
     */
    public User authenticate(LoginDTO input) {
        // Creates an authentication token with the provided email and password and passes it to the AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        // Finds and returns the user from the repository by email if authentication is successful
        // Throws an exception if the user is not found
        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
