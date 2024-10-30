package tradatorii.gym_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tradatorii.gym_management.Entity.Gym;

public interface GymRepo extends JpaRepository<Gym,Long> {
}
