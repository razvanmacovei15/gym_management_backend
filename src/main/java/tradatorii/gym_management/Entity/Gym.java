package tradatorii.gym_management.Entity;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Gyms")
public class Gym {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long gymId;


    @Column(name="address",nullable=false,unique=true)
    private String address;

    @ManyToMany(mappedBy = "gymSet")
    private Set<Task> taskSet=new HashSet<>();

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }
}
