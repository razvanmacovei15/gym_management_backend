package tradatorii.gym_management.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.Repo.GymRepo;



@Service
public class GymService {

    private final GymRepo gymRepo;


    @Autowired
    public GymService(GymRepo gymRepo) {
        this.gymRepo = gymRepo;
    }

    public void deleteGym(Long id) {
        gymRepo.deleteById(id);
    }






}
