package tradatorii.gym_management.Controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Role;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

@RestController
@RequestMapping("api/enum")
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
