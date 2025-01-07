package tradatorii.gym_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findAllByOrderByCreatedAt();
    @Query("SELECT t FROM Task t JOIN t.gymSet g WHERE g.gymId = :gymId")
    List<Task> findTasksByGymId(Long gymId);
    @Query("SELECT t FROM Task t JOIN t.gymSet g WHERE g = :gym")
    List<Task> findAllTasksByGym(Gym gym);
    @Query("SELECT t FROM Task t JOIN t.gymSet g WHERE g.manager.userId = :userId")
    List<Task> findAllTasksByManagerUserId(Long userId);
}
