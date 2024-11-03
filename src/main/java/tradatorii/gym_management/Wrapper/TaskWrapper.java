package tradatorii.gym_management.Wrapper;

import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;

import java.util.HashSet;
import java.util.Set;

public class TaskWrapper {

    private TaskDTO taskDTO;

    private Set<User> usersResponsibleForTask = new HashSet<>();

    public Set<Gym> gymSet = new HashSet<>();


    public TaskDTO getTaskDTO() {
        return taskDTO;
    }

    public void setTaskDTO(TaskDTO taskDTO) {
        this.taskDTO = taskDTO;
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
}


