package tradatorii.gym_management.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import tradatorii.gym_management.Entity.Task;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

    @Override
    public void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
    }

    @Override
    public String uploadTaskFile(Task task, MultipartFile file) throws Exception {
        String bucketName = task.getTaskBucket();
        String objectName = file.getOriginalFilename();
        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
            return objectName;
        }
    }


    @Override
    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Error deleting file", e);
        }
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
    public List<String> getAllFiles(String bucketName) {
        List<String> filenames = new ArrayList<>();
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (found) {
                Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
                for(Result<Item> result : results) {
                    Item item = result.get();
                    System.out.println(item.objectName());
                    filenames.add(item.objectName());
                }
            } else {
                System.out.println("Bucket not found.");
            }
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
        return filenames;
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadObject(String taskBucket, String fileName) {
        try {
            // Get the file stream from MinIO
            InputStream inputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(taskBucket)
                            .object(fileName)
                            .build()
            );

            // Return the stream as a downloadable file
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(inputStream));
        } catch (Exception e) {
            throw new RuntimeException("Error downloading file", e);
        }
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
