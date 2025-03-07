package tradatorii.gym_management.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GymDTO {
    private Long id;
    private String name;
    private String address;
    private Long managerId;
}
