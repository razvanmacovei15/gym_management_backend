package tradatorii.gym_management.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import tradatorii.gym_management.minio.MinioDefaultsProperties;
import tradatorii.gym_management.minio.MinioService;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LogoController {

    private final MinioService minioService;
    private final MinioDefaultsProperties minioDefaults;

    @GetMapping("/logo")
    public ResponseEntity<String> getLogoUrl() {
        try {
            String url = minioService.generatePreSignedUrl(
                    minioDefaults.getBucketName(),
                    minioDefaults.getAppLogo()
            );
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Could not generate logo URL");
        }
    }
}