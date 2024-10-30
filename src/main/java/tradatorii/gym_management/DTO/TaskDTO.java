package tradatorii.gym_management.DTO;

import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Subcatergory;

import java.util.HashSet;
import java.util.Set;

public class TaskDTO {

    private Long taskId;
    private Category category;
    private String description;
    private String deadline;
    private String priority;

    private Set<User> usersResponsibleForTask=new HashSet<>();
    private Subcatergory subcategory;

    public TaskDTO(){}
    public TaskDTO(Long taskId, Category category, String description, String deadline, String priority, Set<User> usersResponsibleForTask, Subcatergory subcategory) {
        this.taskId = taskId;
        this.category = category;
        this.description = description;
        this.deadline = deadline;
        this.priority = priority;
        this.usersResponsibleForTask = usersResponsibleForTask;
        this.subcategory = subcategory;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Set<User> getUsersResponsibleForTask() {
        return usersResponsibleForTask;
    }

    public void setUsersResponsibleForTask(Set<User> usersResponsibleForTask) {
        this.usersResponsibleForTask = usersResponsibleForTask;
    }

    public Subcatergory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcatergory subcategory) {
        this.subcategory = subcategory;
    }
}
