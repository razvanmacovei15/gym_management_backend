package tradatorii.gym_management.minio;

import java.util.List;

public interface MinioService {
    List<MinioFile> listFiles(String bucketName);
    MinioFile getFile(String bucketName, String objectName);
    void uploadFile(String bucketName, String objectName, String filePath);
    void deleteFile(String bucketName, String objectName);

    void createBucket(String bucketName);
    void deleteBucket(String bucketName);

}
