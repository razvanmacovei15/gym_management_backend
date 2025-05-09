package tradatorii.gym_management.Controller;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.Service.implementations.UserService;
import tradatorii.gym_management.minio.MinioService;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final MinioService minioService;

    @PostMapping("/create")
    public User createUser(@RequestBody UserDTO userDTO) {
        log.info("Received request to create user: {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        this.userService.save(user);
        log.info("User created: {}", user);
        return user;
    }

    @PostMapping("/manager")
    public User createManager(@RequestBody UserDTO userDTO) {
        log.info("Received request to create manager: {}", userDTO);
        User user = userMapper.toEntity(userDTO);
        this.userService.createManager(user);
        log.info("Manager created: {}", user);
        return user;
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteUser(@RequestParam Long id) {
        log.info("Received request to delete user with ID: {}", id);
        return ResponseEntity.ok(this.userService.delete(id));
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = this.userService.getAllUsers();
        return userMapper.toDTOList(users);
    }

    @GetMapping("/managers")
    public List<UserDTO> getAllManagers() {
        log.info("Fetching all managers");
        List<User> managers = this.userService.getAllManagers();
        return userMapper.toDTOList(managers);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@RequestParam Long userId, @RequestBody UserDTO userDTO) {
        log.info("Updating user ID {}: {}", userId, userDTO);
        User user = userMapper.toEntity(userDTO);
        User updatedUser = this.userService.update(userId, user);
        UserDTO updatedUserDTO = userMapper.toDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @GetMapping("/createdTasks")
    public List<TaskDTO> getCreatedTasks(@RequestParam Long userId) {
        log.info("Fetching created tasks for user ID: {}", userId);
        Set<Task> tasks = this.userService.getCreatedTasks(userId);
        return taskMapper.taskDTOList(tasks);
    }

    @PostMapping("/changeProfilePicture")
    public String changeProfilePicture(@AuthenticationPrincipal User user, MultipartFile file) {
        try {
            log.info("Changing profile picture for user ID: {}", user.getUserId());
            String userBucket = user.getUserBucket();
            String profilePhotoName = this.userService.generateProfilePhotoName(user);
            String objectName = userService.setProfilePhotoObjectName(profilePhotoName, file);

            userService.uploadProfilePicture(file, user);
            return minioService.generatePreSignedUrl(userBucket, objectName);
        } catch (Exception e) {
            log.error("Error changing profile picture for user ID {}: {}", user.getUserId(), e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @PatchMapping("/updateInformation")
    public ResponseEntity<UserDTO> updateUserInformation(@RequestParam Long userId, @RequestBody UserDTO userDTO) {
        log.info("Updating user information for ID: {}", userId);
        User updatedUser = this.userService.updateUserInformation(userId, userDTO);
        UserDTO updatedUserDTO = userMapper.toDTO(updatedUser);
        return ResponseEntity.ok(updatedUserDTO);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @AuthenticationPrincipal User user) {
        try {
            log.info("Uploading file for user ID: {}", user.getUserId());
            userService.uploadProfilePicture(file, user);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            log.error("Error uploading file for user ID {}: {}", user.getUserId(), e.getMessage(), e);
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }
}


