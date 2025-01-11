package tradatorii.gym_management.Service;

import tradatorii.gym_management.DTO.DashBoard;
import tradatorii.gym_management.DTO.GymBucket;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;

import java.util.List;

public interface GymServiceInterface {
    Gym save(Gym gym);
    Gym getGymById(Long id);
    List<Gym> getAllGyms();
    List<User> getManagersByGymIds(List<Long> gymIds);

    GymBucket getBucket(Long gymId);

    DashBoard getDashBoard();

    GymDTO getGymByUserId(Long userId);
}
