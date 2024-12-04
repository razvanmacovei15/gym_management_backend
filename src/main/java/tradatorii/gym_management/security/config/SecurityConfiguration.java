package tradatorii.gym_management.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    // Custom authentication provider to handle user authentication
    private final AuthenticationProvider authenticationProvider;
    // Custom JWT filter to validate JWT tokens in requests
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    public SecurityConfiguration(
            JwtAuthenticationFilter jwtAuthenticationFilter,
            AuthenticationProvider authenticationProvider
    ) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Defines the main security filter chain for handling authentication, authorization, and other security settings.
     *
     * @param http the HttpSecurity configuration object for customizing security
     * @return a SecurityFilterChain instance representing the configured filter chain
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF (Cross-Site Request Forgery) protection, typically disabled for REST APIs
                .csrf(AbstractHttpConfigurer::disable)
                // Configures access rules for different request patterns
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Allows public access to endpoints under "/auth/**" for login, registration, etc.
                        .anyRequest().authenticated() // Requires authentication for all other endpoints
                )
                // Configures session management to be stateless as JWT tokens are used instead of server-side sessions
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // Sets a custom authentication provider for handling authentication logic
                .authenticationProvider(authenticationProvider)
                // Adds the custom JWT authentication filter before the default UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        // Returns the configured SecurityFilterChain instance
        return http.build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) settings to control which domains can access the API.
     *
     * @return a CorsConfigurationSource instance that contains the CORS configuration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        // Creates a new CorsConfiguration instance to set allowed origins, methods, and headers
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8005")); // Specifies allowed origins (domains) for CORS requests
        configuration.setAllowedMethods(List.of("GET", "POST")); // Specifies allowed HTTP methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type")); // Specifies allowed HTTP headers in requests

        // Registers the CORS configuration for all paths in the application
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
