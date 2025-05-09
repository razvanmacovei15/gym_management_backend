package tradatorii.gym_management.Service;

import tradatorii.gym_management.DTO.DashboardDTO;
import tradatorii.gym_management.DTO.GymStatistics;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;

import java.util.List;

public interface GymServiceInterface {
    Gym save(Gym gym);
    Gym getGymById(Long id);
    List<Gym> getAllGyms();
    List<User> getManagersByGymIds(List<Long> gymIds);

    GymStatistics getGymStatistics(Long gymId);

    DashboardDTO getDashBoard();

    GymDTO getGymByUserId(Long userId);
}
