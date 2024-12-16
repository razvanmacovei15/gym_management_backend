package tradatorii.gym_management.minio;

import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
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

    public void uploadFile(String bucketName, String objectName, MultipartFile file) throws Exception {
        try (InputStream inputStream = file.getInputStream()) {
            String fileType = file.getContentType();
            System.out.println(fileType + "<-- file type");

            //extract extension from filetype
            assert fileType != null;
            String[] fileTypeParts = fileType.split("/");
            System.out.println(Arrays.toString(fileTypeParts) + "<-- file extension");

            String filenameWithExtension = objectName + "." + fileTypeParts[1];
            System.out.println(filenameWithExtension + "<-- filename with extension");

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
