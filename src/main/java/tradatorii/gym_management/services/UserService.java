package tradatorii.gym_management.services;

import tradatorii.gym_management.entity.User;

public interface UserService {
    User save(User user);
    User getUser(Long userId);
}
