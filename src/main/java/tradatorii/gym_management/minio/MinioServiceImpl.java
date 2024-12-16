package tradatorii.gym_management.minio;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Override
    public List<MinioFile> listFiles(String bucketName) {
        return List.of();
    }

    @Override
    public MinioFile getFile(String bucketName, String objectName) {


        return null;
    }

    @Override
    public void uploadFile(String bucketName, String objectName, String filePath) {

    }

    @Override
    public void deleteFile(String bucketName, String objectName) {

    }

    @Override
    public void createBucket(String bucketName) {
        try {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Could not create bucket");
        }
    }

    @Override
    public void deleteBucket(String bucketName) {

    }
}
