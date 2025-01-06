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


public class EnumController {

    @GetMapping("/roles")
    public Role[] getRoles() {

        return Role.values();
    }

    @GetMapping("/statuses")
    public Status[] getTaskStatus() {
        return Status.values();
    }

    @GetMapping("/categories")
    public Category[] getCategory() {
        return Category.values();
    }


}
