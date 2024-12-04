package tradatorii.gym_management.Service;

import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserServiceInterface {
    User save(User user);
    User update(Long id, User user);
    Long delete(Long id);
    Optional<User> getById(Long id);
    List<User> getAllUsers();

    Set<Task> getCreatedTasks(Long userId);

}
