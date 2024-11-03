package tradatorii.gym_management.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor

@Entity
@Table(name="Tasks")
public class Task {

    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Id
    private Long taskId;

    @Column(name="category",nullable=false,unique = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name="description",nullable=true,unique=false)
    private String description;

    @Column(name="deadline",nullable=true,unique=false)
    private String deadline;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @Column(name="priority",nullable=false,unique=false)
    private String priority;

    @Column(name="status",nullable=false,unique=false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name="UsersTasks",
            joinColumns = @JoinColumn(name= "taskId"),
            inverseJoinColumns = @JoinColumn(name = "userId"))
    private Set<User> usersResponsibleForTask=new HashSet<>();


    @ManyToMany
    @JoinTable(
            name="GymTasks",
            joinColumns = @JoinColumn(name= "taskId"),
            inverseJoinColumns = @JoinColumn(name = "gymId"))
    private Set<Gym> gymSet=new HashSet<>();

    @Column(name="subcategory")
    @Enumerated(EnumType.STRING)
    private Subcategory subcategory;

    @Column(name="createdAt",nullable=false,unique=false)
    private String createdAt;

    @Column(name="updatedAt",nullable=false,unique=false)
    private String updatedAt;


    public Task(){}

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<User> getUsersResponsibleForTask() {
        return usersResponsibleForTask;
    }

    public void setUsersResponsibleForTask(Set<User> usersResponsibleForTask) {
        this.usersResponsibleForTask = usersResponsibleForTask;
    }

    public Set<Gym> getGymSet() {
        return gymSet;
    }

    public void setGymSet(Set<Gym> gymSet) {
        this.gymSet = gymSet;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }
}
