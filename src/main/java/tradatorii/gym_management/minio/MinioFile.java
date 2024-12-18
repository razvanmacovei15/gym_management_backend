package tradatorii.gym_management.minio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
@Component
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class MinioFile {
    private String bucketName;
    private String objectName;
    private Long objectSize;
    private ZonedDateTime lastModified;
}
