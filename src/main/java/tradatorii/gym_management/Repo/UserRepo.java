package tradatorii.gym_management.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Role role);

}
