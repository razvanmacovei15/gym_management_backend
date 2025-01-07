package tradatorii.gym_management.Service.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Repo.GymRepo;
import tradatorii.gym_management.Service.GymServiceInterface;

import java.util.List;


@Service
public class GymService implements GymServiceInterface {
    private final GymRepo gymRepo;
    @Override
    public Gym save(Gym gym) {
        return gymRepo.save(gym);
    }

    @Override
    public Gym getGymById(Long id) {
        return gymRepo.getGymByGymId(id);
    }

    @Override
    public List<Gym> getAllGyms() {
        return gymRepo.findAll();
    }

    @Autowired
    public GymService(GymRepo gymRepo) {
        this.gymRepo = gymRepo;
    }

    public void deleteGym(Long id) {
        gymRepo.deleteById(id);
    }

    public List<User> getManagersByGymIds(List<Long> gymIds) {
        return gymRepo.findManagersByGymIds(gymIds);
    }

    @Override
    public Gym getGymByUserId(User user) {
        return gymRepo.findGymByManager(user);
    }


}
