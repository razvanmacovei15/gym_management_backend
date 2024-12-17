package tradatorii.gym_management.DTO;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class UpdateUserProfileDTO {
    private MultipartFile file;
    private String name;
    private String email;
}
