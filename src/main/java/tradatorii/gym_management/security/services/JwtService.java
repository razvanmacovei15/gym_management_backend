package tradatorii.gym_management.security.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    // Secret key for signing the JWT, injected from application properties
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    // JWT expiration time in milliseconds, injected from application properties
    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the username stored as the subject in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    /**
     * Extracts a specific claim from the token using a function resolver.
     *
     * @param token the JWT token from which to extract the claim
     * @param claimsResolver function to retrieve the claim value
     * @param <T> the type of the claim
     * @return the resolved claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for a specific user with default claims.
     *
     * @param userDetails the user details for whom to generate the token
     * @return a JWT token as a String
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with additional claims.
     *
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details for whom to generate the token
     * @return a JWT token as a String
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, jwtExpiration);
    }

    /**
     * Gets the configured expiration time for JWTs.
     *
     * @return the JWT expiration time in milliseconds
     */
    public long getExpirationTime() {
        return jwtExpiration;
    }

    /**
     * Builds a JWT token with specified claims, subject, and expiration time.
     * @param extraClaims additional claims to include in the token
     * @param userDetails the user details to include in the token
     * @param expiration the token's expiration time in milliseconds
     * @return a JWT token as a String
     */
    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)                             // Set custom claims in the token
                .setSubject(userDetails.getUsername())              // Set the username as the subject
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Set the current date as the issue date
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // Set expiration date
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Sign the token with the secret key using HS256
                .compact();
    }

    /**
     * Validates a token by checking if it's not expired and if it matches the user details.
     * @param token the JWT token to validate
     * @param userDetails the user details to match against the token
     * @return true if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token has expired.
     * @param token the JWT token to check
     * @return true if the token has expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token.
     * @param token the JWT token from which to extract the expiration date
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Parses the token and extracts all claims contained within it.
     * @param token the JWT token to parse
     * @return all claims from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())  // Set the signing key to validate the token signature
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Decodes the secret key and returns it as a Key object.
     * @return the decoded secret key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Decode the base64-encoded secret key
        return Keys.hmacShaKeyFor(keyBytes);                 // Generate a key for HS256 algorithm
    }
}
