package tradatorii.gym_management.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Service.implementations.UserService;
import tradatorii.gym_management.minio.MinioService;

@RequestMapping("/minio")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:8020", // Replace with your frontend's URL
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")
public class MinioController {

    private final MinioService minioService;
    private final UserService userService;

    @PostMapping("/createBucket")
    public void createBucket(@RequestParam String bucketName) {
        minioService.createBucket(bucketName);
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(
//            @RequestParam("bucketName") String bucketName,
//            @RequestParam("objectName") String objectName,
            @RequestParam("file") MultipartFile file, @AuthenticationPrincipal User user) {
        try {
            String bucketName = user.getUserBucket();
            String objectName = userService.generateProfilePhotoName(user);
            minioService.uploadFile(bucketName, objectName, file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

    @GetMapping("/generate-url")
    public String generatePreSignedUrl(@RequestParam String bucketName, @RequestParam String objectName) throws Exception {
        return minioService.generatePreSignedUrl(bucketName, objectName);
    }
}
