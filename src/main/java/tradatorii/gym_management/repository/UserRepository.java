package tradatorii.gym_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tradatorii.gym_management.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
