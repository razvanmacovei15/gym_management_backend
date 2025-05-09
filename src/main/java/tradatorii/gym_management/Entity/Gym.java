package tradatorii.gym_management.Entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name="Gyms")
public class Gym {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long gymId;

    @Column(name="name",nullable=false,unique=true)
    private String name;


    @Column(name="address",nullable=false,unique=true)
    private String address;

    @OneToOne(optional = false)
    @JoinColumn(name = "manager_id", nullable = false, unique = true)
    private User manager;

    @ManyToMany(mappedBy = "gymSet")
    private Set<Task> taskSet = new HashSet<>();

}
//@user_access_key()
//class SpotifyClient {
//
