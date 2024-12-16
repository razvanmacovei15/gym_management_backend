package tradatorii.gym_management.minio;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MinioService {
    List<MinioFile> listFiles(String bucketName);
    MinioFile getFile(String bucketName, String objectName);
    void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception;
    void deleteFile(String bucketName, String objectName);

    void createBucket(String bucketName);
    void deleteBucket(String bucketName);

}
