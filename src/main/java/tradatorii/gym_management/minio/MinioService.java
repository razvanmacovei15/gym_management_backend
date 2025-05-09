package tradatorii.gym_management.minio;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tradatorii.gym_management.Entity.Task;

import java.util.List;

public interface MinioService {
    List<String> listFiles(String bucketName);
    String getFile(String bucketName, String objectName);
    void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception;
    String uploadTaskFile(Task task, MultipartFile file) throws Exception;
    void deleteFile(String bucketName, String objectName);
    String generatePreSignedUrl(String bucketName, String objectName) throws Exception;
    String createBucket(String bucketName);
    void deleteBucket(String bucketName);
    List<String> getAllFiles(String bucketName);
    boolean bucketExists(String bucketName);


    ResponseEntity<InputStreamResource> downloadObject(String taskBucket, String fileName);
}