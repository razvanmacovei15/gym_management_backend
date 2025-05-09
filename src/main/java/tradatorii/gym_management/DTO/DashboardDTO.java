package tradatorii.gym_management.DTO;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDTO {

    Integer totalGyms;
    List<GymStatistics> allGymsStatistics;

}
