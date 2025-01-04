package tradatorii.gym_management.minio;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioService {
    List<String> listFiles(String bucketName);
    String getFile(String bucketName, String objectName);
    void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception;
    void deleteFile(String bucketName, String objectName);
    String generatePreSignedUrl(String bucketName, String objectName) throws Exception;
    String createBucket(String bucketName);
    void deleteBucket(String bucketName);
}