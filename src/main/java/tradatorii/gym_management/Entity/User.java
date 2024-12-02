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



@Entity
@Table(name="Users")
public class User {

    //TODO: ADD PASSWORD
    
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
    private Set<Task> tasksResponsibleFor = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Task> tasksCreated=new HashSet<>();


    public User(){}
    public User(String name,String email,Role role)
    {
        this.name=name;
        this.email=email;
        this.role=role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
