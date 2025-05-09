package tradatorii.gym_management.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio.default")
public class MinioDefaultsProperties {
    private String bucketName;
    private String profileImage;
    private String appLogo;
}