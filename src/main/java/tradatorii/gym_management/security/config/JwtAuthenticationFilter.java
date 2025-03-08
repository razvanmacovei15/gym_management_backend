package tradatorii.gym_management.security.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import tradatorii.gym_management.security.services.JwtService;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    // Handler to resolve exceptions and send appropriate error responses
    private final HandlerExceptionResolver handlerExceptionResolver;
    // Service for handling JWT operations such as extracting and validating tokens
    private final JwtService jwtService;
    // Service for loading user details based on their username (email in this case)
    private final UserDetailsService userDetailsService;
    public JwtAuthenticationFilter(
            JwtService jwtService,
            UserDetailsService userDetailsService,
            HandlerExceptionResolver handlerExceptionResolver
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Core method to filter each request for JWT authentication.
     * Extracts the JWT token, verifies it, loads the user, and sets the authentication context.
     *
     * @param request     the HTTP request being processed
     * @param response    the HTTP response being generated
     * @param filterChain the filter chain to continue processing if authentication is valid
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Get the Authorization header from the request, which should contain the JWT token
        final String authHeader = request.getHeader("Authorization");
        // If the header is missing or doesn't start with "Bearer ", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            // Extract the JWT token by removing "Bearer " prefix
            final String jwt = authHeader.substring(7);
            // Extract the username (email) from the JWT token
            final String userEmail = jwtService.extractUsername(jwt);
            // Retrieve the current authentication context from the SecurityContextHolder
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            // If the username is found and no other authentication is set in the context, proceed
            if (userEmail != null && authentication == null) {
                // Load user details using the extracted username (email) from the token
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                // Validate the JWT token using the JwtService
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    // Create an authentication token for the user with their authorities (roles/permissions)
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,  // No credentials are required as we're authenticated via JWT
                            userDetails.getAuthorities()
                    );
                    // Attach request-specific details to the token, such as IP address and session ID
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Set the authentication token in the SecurityContextHolder to mark the user as authenticated
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            System.out.println("🔍 JWT Filter: Request received for " + request.getRequestURI());

            // Continue with the filter chain after setting authentication
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            // In case of any exception, handle it with the injected HandlerExceptionResolver
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
