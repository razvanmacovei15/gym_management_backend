package tradatorii.gym_management.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import tradatorii.gym_management.Enums.Role;

import java.nio.file.FileStore;
import java.util.Objects;


@Builder
@Getter
@Setter

public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    // Default constructor
    public UserDTO() {}

    // Parameterized constructor
    public UserDTO(Long id, String name, String email,String role) {
        this.name = name;
        this.email = email;
        this.role=role;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Overriding toString() for debugging or logging purposes
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Overriding equals() and hashCode() for comparison and collection handling
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return id.equals(userDTO.id) && name.equals(userDTO.name) && email.equals(userDTO.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }

    public String getRole() {
        return role;
    }
}
