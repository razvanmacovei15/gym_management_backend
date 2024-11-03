package tradatorii.gym_management.Controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.DTO.TaskRequestDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Service.TaskServiceInterface;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskServiceInterface taskService;
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO)
    {
        TaskDTO taskDTO = taskRequestDTO.getTaskDTO();
        Task task = taskMapper.toEntity(taskDTO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        task.setStatus(Status.PENDING);

        Set<GymDTO> gyms = taskRequestDTO.getGyms();
        Set<UserDTO> users = taskRequestDTO.getUsers();

        Set<Gym> gymSet = gyms.stream().map(gymDTO -> Gym.builder()
                .gymId(gymDTO.getId())
                .name(gymDTO.getName())
                .address(gymDTO.getAddress())
                .build()).collect(Collectors.toSet());
        Set<User> usersSet = users.stream().map(userDTO -> User.builder()
                .userId(userDTO.getId())
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .build()).collect(Collectors.toSet());

        task.setUsersResponsibleForTask(usersSet);
        task.setGymSet(gymSet);

        Task savedTask = taskService.save(task);
        System.out.println(taskDTO);
        return ResponseEntity.ok(taskMapper.mapFrom(savedTask));

    }


}
