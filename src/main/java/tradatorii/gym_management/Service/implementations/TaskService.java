package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.DTO.TaskRequestDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Repo.GymRepo;
import tradatorii.gym_management.Repo.TaskRepo;
import tradatorii.gym_management.Repo.UserRepo;
import tradatorii.gym_management.Service.TaskServiceInterface;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService implements TaskServiceInterface {

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final GymRepo gymRepo;



    @Override
    public Task save(Task task) {
        return taskRepo.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    public Task createTask(Task task) {
        return taskRepo.save(task);
    }

    public Long deleteTask(Long id) {
        taskRepo.deleteById(id);
        return id;
    }

    public Status updateStatus(Long id, Status status) {
        Task task = taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));
        task.setStatus(status);
        taskRepo.save(task);
        return status;
    }

    public Task updateTask(Long id, Task task) {
        return taskRepo.findById(id)
                .map(t -> {
                    t.setCategory(task.getCategory());
                    t.setDescription(task.getDescription());
                    t.setDeadline(task.getDeadline());
                    t.setPriority(task.getPriority());
                    t.setStatus(task.getStatus());
                    t.setUsersResponsibleForTask(task.getUsersResponsibleForTask());
                    t.setGymSet(task.getGymSet());
                    return taskRepo.save(t);
                })
                .orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    @Override
    public Task createNewTask(TaskRequestDTO taskDTO) {
        // Map TaskDTO to Task Entity
        Task task = Task.builder()
                .title(taskDTO.getTaskDTO().getTitle())
                .category(taskDTO.getTaskDTO().getCategory())
                .description(taskDTO.getTaskDTO().getDescription())
                .deadline(taskDTO.getTaskDTO().getDeadline())
                .priority(taskDTO.getTaskDTO().getPriority())
                .status(Status.TO_DO) // Default status
                .build();

        // Fetch Users and Set to Task
        Set<User> users = new HashSet<>();
        for (UserDTO userDTO : taskDTO.getUsers()) {
            Optional<User> user = userRepo.findById(userDTO.getId());
            user.ifPresent(users::add);
        }
        task.setUsersResponsibleForTask(users);

        // Fetch Gyms and Set to Task
        Set<Gym> gyms = new HashSet<>();
        for (GymDTO gymDTO : taskDTO.getGyms()) {
            Optional<Gym> gym = gymRepo.findById(gymDTO.getId());
            gym.ifPresent(gyms::add);
        }
        task.setGymSet(gyms);

        System.out.println("hai sa dam mana cu mana si sa dam merge pe master");

        // Save and return the task
        return taskRepo.save(task);
    }

}
