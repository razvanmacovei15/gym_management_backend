package tradatorii.gym_management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tradatorii.gym_management.Enums.Role;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;



@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;

    @Column(name="name",nullable = false,unique = false)
    private String name;

    @Column(name="email",nullable=false,unique=true)
    private String email;

    @Column(name="password",nullable=false,unique=false)
    private String password;

    @Column(name="role",nullable=false,unique=false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToMany(mappedBy = "usersResponsibleForTask")
    private Set<Task> tasksResponsibleFor;

    @OneToMany(mappedBy = "user")
    private Set<Task> tasksCreated;

    @Column(name="userBucket",nullable=true,unique=false)
    private String userBucket;

    @Column(name="photoUrl",nullable=true,unique=false)
    private String profilePhotoObjectName;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
