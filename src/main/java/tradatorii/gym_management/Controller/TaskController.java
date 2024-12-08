package tradatorii.gym_management.Controller;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", // Replace with your frontend's URL
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true")

public class TaskController {

    private TaskServiceInterface taskService;
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO)
    {
        TaskDTO taskDTO = taskRequestDTO.getTaskDTO();
        Task task = taskMapper.toEntity(taskDTO);

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
                .role(userDTO.getRole())
                .build()).collect(Collectors.toSet());

        task.setUsersResponsibleForTask(usersSet);

        task.setGymSet(gymSet);

        Task savedTask = taskService.save(task);

        System.out.println(taskDTO + "<= adding this task worked");
        return ResponseEntity.ok(taskMapper.mapFrom(savedTask));

    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskRequestDTO>> getAllTasks()
    {
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks.stream().map(taskMapper::mapToRequest).collect(Collectors.toList()));
    }


    @PatchMapping("/updateStatus")
    public ResponseEntity<Status> updateStatus(@RequestParam Long id, @RequestParam Status status)
    {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @PatchMapping("/update")
    public ResponseEntity<TaskDTO> updateTask(@RequestParam Long taskId, @RequestBody TaskDTO taskDTO)
    {
        Task task = taskMapper.toEntity(taskDTO);
        Task updatedTask = taskService.updateTask(taskId, task);
        TaskDTO updatedTaskDTO = taskMapper.mapFrom(updatedTask);
        return ResponseEntity.ok(updatedTaskDTO);

    }


}
