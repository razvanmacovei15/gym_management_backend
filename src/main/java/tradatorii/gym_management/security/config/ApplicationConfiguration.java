package tradatorii.gym_management.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tradatorii.gym_management.Repo.UserRepo;

@Configuration
public class ApplicationConfiguration {
    private final UserRepo userRepository;
    public ApplicationConfiguration(UserRepo userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Defines the UserDetailsService bean, which loads user-specific data.
     * Finds users by their email (used as the username here).
     *
     * @return a UserDetailsService that fetches user details by email
     */

    @Bean
    UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Defines the BCryptPasswordEncoder bean for encoding passwords.
     * BCrypt is a hashing algorithm that secures passwords by encoding them.
     *
     * @return an instance of BCryptPasswordEncoder
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines the AuthenticationManager bean, which manages authentication requests.
     * Uses the provided AuthenticationConfiguration to get the default AuthenticationManager.
     *
     * @param config the authentication configuration
     * @return an instance of AuthenticationManager
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Defines the AuthenticationProvider bean that uses DaoAuthenticationProvider.
     * It relies on UserDetailsService and BCryptPasswordEncoder for authentication.
     * The DaoAuthenticationProvider will authenticate users based on the data retrieved
     * by the UserDetailsService and check passwords with the password encoder.
     *
     * @return an AuthenticationProvider using DAO-based authentication
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Set the custom UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder());       // Set the BCrypt password encoder
        return authProvider;
    }
}
