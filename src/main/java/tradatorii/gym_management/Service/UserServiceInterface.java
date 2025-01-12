package tradatorii.gym_management.Service;

import org.springframework.web.multipart.MultipartFile;
import tradatorii.gym_management.DTO.UserDTO;
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
    List<User> getAllManagers();
    User createManager(User user);
    Set<Task> getCreatedTasks(Long userId);
    String createUserBucket(User user);
    void setDefaultProfilePhoto(User user);
    String generateProfilePhotoName(User user);
    String changeProfilePicture(String objectName);
    User updateUserInformation(Long id, UserDTO updateDTO);
    String generatePreSignedUrl(User user);
    String setProfilePhotoObjectName(String objectName, MultipartFile file) throws Exception;
}
