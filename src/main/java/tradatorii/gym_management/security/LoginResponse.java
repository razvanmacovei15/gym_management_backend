package tradatorii.gym_management.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LoginResponse {
    private String token;
    private long expiresIn;

}
