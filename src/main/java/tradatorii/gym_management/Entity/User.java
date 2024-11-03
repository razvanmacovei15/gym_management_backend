package tradatorii.gym_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tradatorii.gym_management.Enums.Role;

import java.util.HashSet;
import java.util.Set;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column(name="name",nullable = false,unique = false)
    private String name;

    @Column(name="email",nullable=false,unique=true)
    private String email;

    @Column(name="role",nullable=false,unique=false)
    @Enumerated(EnumType.STRING)
    private Role role;


    @ManyToMany(mappedBy = "usersResponsibleForTask")
    private Set<Task> tasksResponsibleFor;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasksCreated;

}
