package tradatorii.gym_management.minio;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MinioServiceImpl implements MinioService {

    private final MinioClient minioClient;

    @Override
    public List<String> listFiles(String bucketName) {
        return List.of();
    }

    @Override
    public String getFile(String bucketName, String objectName) {
        return null;
    }

    public void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            String fileType = file.getContentType();

            //extract extension from filetype
            assert fileType != null;
            String[] fileTypeParts = fileType.split("/");

            String filenameWithExtension = objectName + "." + fileTypeParts[1];

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filenameWithExtension)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
    }

    @Override
    public void deleteFile(String bucketName, String objectName) {

    }

    @Override
    public String createBucket(String bucketName) {
        try {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
            return bucketName;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Could not create bucket");
        }
    }

    @Override
    public void deleteBucket(String bucketName) {

    }

    @Override
    public String generatePreSignedUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .method(Method.GET)
                        .expiry(2, TimeUnit.HOURS) // URL valid for 2 hours
                        .build()
        );
    }
}
