package tradatorii.gym_management.Entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Enums.Subcategory;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
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
    @CreationTimestamp
    @Column(name="createdAt",nullable=false,unique=false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name="updatedAt",nullable=false,unique=false)
    private LocalDateTime updatedAt;

}
