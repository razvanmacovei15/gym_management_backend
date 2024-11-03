package tradatorii.gym_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tradatorii.gym_management.Entity.Gym;
@Repository
public interface GymRepo extends JpaRepository<Gym,Long> {
}
