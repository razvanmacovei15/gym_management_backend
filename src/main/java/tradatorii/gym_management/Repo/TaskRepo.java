package tradatorii.gym_management.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import tradatorii.gym_management.Entity.Task;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task,Long> {
    List<Task> findAllByOrderByCreatedAt();
}
