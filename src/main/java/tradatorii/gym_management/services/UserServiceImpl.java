package tradatorii.gym_management.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.entity.User;
import tradatorii.gym_management.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public User save(User user){
        return userRepository.save(user);
    }
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }
}
