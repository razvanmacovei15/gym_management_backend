package tradatorii.gym_management.Service.implementations;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;
import tradatorii.gym_management.Repo.UserRepo;
import tradatorii.gym_management.Service.UserServiceInterface;
import tradatorii.gym_management.minio.MinioService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService implements UserServiceInterface {
    private final UserRepo userRepository;
    private final MinioService minioService;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    public List<User> getAllUsers() {
        log.debug("Retrieving all users");
        return userRepository.findAll();
    }

    public List<User> getAllManagers() {
        log.debug("Retrieving all managers");
        return userRepository.findAllByRole(Role.MANAGER);
    }

    @Override
    public Optional<User> getById(Long id) {
        log.debug("Retrieving user by ID: {}", id);
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        log.info("Saving user: {}", user);
        return userRepository.save(user);
    }
    @Override
    public User update(Long id, User updatedUser) {
        log.info("Updating user ID {}: {}", id, updatedUser);
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setRole(updatedUser.getRole());
                    log.info("User updated: {}", user);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new RuntimeException("User not found with id " + id);
                });
    }

    @Override
    public Long delete(Long id) {
        log.info("Deleting user ID: {}", id);
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public Set<Task> getCreatedTasks(Long userId) {
        log.debug("Retrieving created tasks for user ID: {}", userId);
        return userRepository.findById(userId)
                .map(User::getTasksCreated)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", userId);
                    return new RuntimeException("User not found with id " + userId);
                });
    }

    @Override
    public String createUserBucket(User user) {
        String bucketName = user.getName().toLowerCase() + "-" + user.getUserId() + "-bucket";
        return minioService.createBucket(bucketName);
    }

    @Override
    public void setDefaultProfilePhoto(User user) {
        user.setProfilePhotoObjectName("defaultProfilePhoto.png");
    }

    @Override
    public String changeProfilePicture(String objectName) {
        return null;
    }

    @Override
    public User createManager(User user) {
        log.info("Creating manager from user input: {}", user);
        return User.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(Role.MANAGER)
                .build();
    }

    @Override
    public String generateProfilePhotoName(User user) {
        return user.getName() + user.getUserId() + "-profilePhoto";
    }

    @Override
    public User updateUserInformation(Long id, UserDTO updateDTO) {
        log.info("Updating user information for ID {}: {}", id, updateDTO);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with ID: {}", id);
                    return new IllegalArgumentException("User not found with ID: " + id);
                });

        if (updateDTO.getName() != null) {
            existingUser.setName(updateDTO.getName());
        }
        if (updateDTO.getEmail() != null) {
            existingUser.setEmail(updateDTO.getEmail());
        }

        return userRepository.save(existingUser);
    }


    public String generatePreSignedUrl(User user) {
        log.debug("Generating pre-signed URL for user ID: {}", user.getUserId());
        try {
            return "defaultProfilePhoto.png".equals(user.getProfilePhotoObjectName())
                    ? minioService.generatePreSignedUrl("default-values", "defaultProfilePhoto.png")
                    : minioService.generatePreSignedUrl(user.getUserBucket(), user.getProfilePhotoObjectName());
        } catch (Exception e) {
            log.error("Error generating pre-signed URL for user ID {}: {}", user.getUserId(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public String setProfilePhotoObjectName(String objectName, MultipartFile file) {
        try (InputStream inputStream = file.getInputStream()) {
            String fileType = file.getContentType();

            //extract extension from filetype
            assert fileType != null;
            String[] fileTypeParts = fileType.split("/");

            return objectName + "." + fileTypeParts[1];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void uploadProfilePicture(MultipartFile file, User user) {
        String bucketName = user.getUserBucket();
        String objectName = generateProfilePhotoName(user);
        String photoMinioObject;

        // Generate object name with extension
        photoMinioObject = setProfilePhotoObjectName(objectName, file);

        // Upload to Minio
        try {
            minioService.uploadFile(bucketName, photoMinioObject, file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Update user profile
        user.setProfilePhotoObjectName(photoMinioObject);
        save(user);
    }

}


