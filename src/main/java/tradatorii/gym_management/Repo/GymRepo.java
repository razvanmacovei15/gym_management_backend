package tradatorii.gym_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tradatorii.gym_management.DTO.GymBucket;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;

import java.util.List;

@Repository
public interface GymRepo extends JpaRepository<Gym,Long> {
    @Query("SELECT g.manager FROM Gym g WHERE g.gymId IN :gymIds")
    List<User> findManagersByGymIds(List<Long> gymIds);
    Gym getGymByGymId(Long gymId);
    Gym findGymByManager(User manager);




}
