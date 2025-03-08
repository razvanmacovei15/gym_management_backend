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
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Disables CSRF (Cross-Site Request Forgery) protection, typically disabled for REST APIs
                .csrf(AbstractHttpConfigurer::disable)
                // Configures access rules for different request patterns
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register","/api/hello", "/hello-page").permitAll() // Public endpoints
                        .requestMatchers("/auth/me").authenticated() // Secure endpoint
                        .anyRequest().authenticated()
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
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow specific frontend origin instead of "*"
        configuration.setAllowedOrigins(List.of("http://localhost:8020/gymapp/")); // Set exact origin

        // Allow standard HTTP methods
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // Allow all headers (fix for potential CORS issues)
        configuration.setAllowedHeaders(List.of("*"));

        // Allow credentials (important if using authentication tokens or cookies)
        configuration.setAllowCredentials(true);

        // Apply CORS configuration to all endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}
