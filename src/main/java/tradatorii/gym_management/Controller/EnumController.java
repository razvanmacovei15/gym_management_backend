package tradatorii.gym_management.Controller;


import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Role;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;
import tradatorii.gym_management.minio.MinioConfig;
import tradatorii.gym_management.minio.MinioService;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("api/enum")
@CrossOrigin(origins = "http://localhost:8020", // Replace with your frontend's URL
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")

public class EnumController {

    private final MinioService minioService;

    // Constructor injection of MinioClient
    public EnumController(MinioService minioService) {
        this.minioService = minioService;
    }

    @GetMapping("/roles")
    public Role[] getRoles() {
//        try {
//            // Specify the file path
//            String filePath = "src/main/resources/React-App-Development-Update.pptx";
//            File file = new File(filePath);
//
//            // Create a FileInputStream for the file
//            InputStream inputStream = new FileInputStream(file);
//
//            // Get the size of the file
//            long objectSize = file.length();
//
//            // Define the part size for multipart uploads (e.g., 5MB)
//            long partSize = 5 * 1024 * 1024;
//
//            // Upload the object to the bucket
//            minioClient.putObject(
//                    PutObjectArgs.builder()
//                            .bucket("test1")
//                            .object("point.pptx")
//                            .stream(inputStream, objectSize, partSize)
//                            .contentType("application/pptx") // Set the content type if known
//                            .build()
//            );
//
//            // Close the InputStream after the upload is complete
//            inputStream.close();
//        } catch (Exception e) {
//            System.out.println("An error occurred during the upload process");
//            System.out.println(e.getMessage());
//        }
        minioService.createBucket("test5");

        return Role.values();
    }

    @GetMapping("/taskStatus")
    public Status[] getTaskStatus() {
        return Status.values();
    }

    @GetMapping("/category")
    public Category[] getCategory() {
        return Category.values();
    }

    @GetMapping("/subcategory")
    public Subcategory[] getSubcategory() {
        return Subcategory.values();
    }


}
