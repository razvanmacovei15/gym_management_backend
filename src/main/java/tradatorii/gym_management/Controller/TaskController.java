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
import tradatorii.gym_management.Service.GymServiceInterface;
import tradatorii.gym_management.Service.TaskServiceInterface;
import tradatorii.gym_management.Service.UserServiceInterface;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
@CrossOrigin(
        origins = "http://localhost:8020", // Frontend URL
        allowedHeaders = {"Authorization", "Content-Type"},
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE},
        allowCredentials = "true"
)


public class TaskController {

    private TaskServiceInterface taskService;
    private UserServiceInterface userService;
    private final GymServiceInterface gymService;
    private final TaskMapper taskMapper;

    @PostMapping("/create")
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequestDTO taskRequestDTO)
    {
        Task task = taskService.createNewTask(taskRequestDTO);
        TaskDTO taskDTO = taskMapper.mapFrom(task);
        return ResponseEntity.ok(taskDTO);

    }

    @GetMapping("/all")
    public ResponseEntity<List<TaskDTO>> getAllTasks()
    {
        List<Task> tasks = taskService.findAllOrderByCreatedAtDesc();
        return ResponseEntity.ok(tasks.stream().map(taskMapper::mapFrom).collect(Collectors.toList()));
    }


    @PatchMapping("/updateStatus")
    public ResponseEntity<Status> updateStatus(@RequestParam Long id, @RequestParam Status status)
    {
        return ResponseEntity.ok(taskService.updateStatus(id, status));
    }

    @PatchMapping("/update")
    public ResponseEntity<TaskDTO> updateTask(@RequestParam Long taskId, @RequestBody TaskDTO taskDTO)
    {
        System.out.println("TaskDTO: " + taskDTO);

        // Get the existing task from the database
        Task existingTask = taskService.getTaskById(taskId);

        // Update the basic properties of the task
        existingTask.setTitle(taskDTO.getTitle());
        existingTask.setCategory(taskDTO.getCategory());
        existingTask.setDescription(taskDTO.getDescription());
        existingTask.setDeadline(taskDTO.getDeadline());
        existingTask.setPriority(taskDTO.getPriority());
        existingTask.setStatus(taskDTO.getStatus());

        // Update the gym set
        Set<Gym> gymsToUpdate = taskDTO.getGyms().stream()
                .map(gymDTO -> gymService.getGymById(gymDTO.getId()))
                .collect(Collectors.toSet());
        existingTask.setGymSet(gymsToUpdate);

        // Update the users set
        Set<User> usersToUpdate = taskDTO.getUsers().stream()
                .map(userDTO -> userService.getById(userDTO.getId()).orElseThrow(() -> new RuntimeException("User not found")))
                .collect(Collectors.toSet());
        existingTask.setUsersResponsibleForTask(usersToUpdate);

        // Save the updated task
        Task updatedTask = taskService.updateTask(taskId, existingTask);

        // Convert back to DTO and return
        TaskDTO updatedTaskDTO = taskMapper.mapFrom(updatedTask);
        System.out.println(updatedTaskDTO);

        return ResponseEntity.ok(updatedTaskDTO);

    }

    @DeleteMapping("/delete")
    public ResponseEntity<Long> deleteTask(@RequestParam Long id)
    {
        return ResponseEntity.ok(taskService.deleteTask(id));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TaskDTO> getTask(@PathVariable Long id)
    {
        Task task = taskService.getTaskById(id);
        TaskDTO taskDTO = taskMapper.mapFrom(task);
        return ResponseEntity.ok(taskDTO);
    }


}
