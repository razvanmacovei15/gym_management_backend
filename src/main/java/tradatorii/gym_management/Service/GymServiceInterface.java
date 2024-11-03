package tradatorii.gym_management.Service;

import tradatorii.gym_management.Entity.Gym;

import java.util.List;

public interface GymServiceInterface {
    Gym save(Gym gym);
    List<Gym> getAllGyms();

}
