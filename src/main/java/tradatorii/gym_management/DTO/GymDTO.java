package tradatorii.gym_management.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GymDTO {
    private Long id;
    private String name;
    private String address;
}
