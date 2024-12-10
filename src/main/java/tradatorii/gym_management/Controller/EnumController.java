package tradatorii.gym_management.Controller;


import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Role;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

@RestController
@RequestMapping("api/enum")
@CrossOrigin(origins = "http://localhost:8020", // Replace with your frontend's URL
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")

public class EnumController {

    @GetMapping("/roles")
    public Role[] getRoles() {
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
