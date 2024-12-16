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
