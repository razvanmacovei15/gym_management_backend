package tradatorii.gym_management.DTO;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashBoard {

    Integer totalGyms;
    List<GymBucket> bucketList;

}
