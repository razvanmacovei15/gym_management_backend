package tradatorii.gym_management.Controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import tradatorii.gym_management.minio.MinioDefaultsProperties;
import tradatorii.gym_management.minio.MinioService;

import java.util.logging.Logger;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class LogoController {

    private final MinioService minioService;
    private final MinioDefaultsProperties minioDefaults;

    @GetMapping("/logo")
    public ResponseEntity<String> getLogoUrl() {
        log.info("GET /api/logo called");
        try {
            String bucket = minioDefaults.getBucketName();
            String object = minioDefaults.getAppLogo();
            log.info("Attempting to generate pre-signed URL for bucket: {}, object: {}", bucket, object);

            String url = minioService.generatePreSignedUrl(bucket, object);
            log.info("Generated pre-signed URL: {}", url);

            return ResponseEntity.ok(url);
        } catch (Exception e) {
            log.error("Failed to generate pre-signed logo URL", e);
            return ResponseEntity.internalServerError().body("Could not generate logo URL");
        }
    }
}