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
    private int gymId;

    @Column(name="name",nullable=false,unique=true)
    private String name;


    @Column(name="address",nullable=false,unique=true)
    private String address;

    @ManyToMany(mappedBy = "gymSet")
    private Set<Task> taskSet=new HashSet<>();


}
