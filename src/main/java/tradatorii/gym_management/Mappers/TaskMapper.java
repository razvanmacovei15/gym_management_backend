package tradatorii.gym_management.Mappers;
import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.DTO.TaskRequestDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Wrapper.TaskWrapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TaskMapper {

    UserMapper userMapper = new UserMapper();
    GymMapper gymMapper = new GymMapper();
    public Task toEntity(TaskDTO taskDTO){
        return Task.builder()
                .category(taskDTO.getCategory())
                .description(taskDTO.getDescription())
                .deadline(taskDTO.getDeadline())
                .priority(taskDTO.getPriority())
                .status(taskDTO.getStatus())
                .build();
    }


    public TaskDTO mapFrom(Task task){
        Set<Gym> gyms = task.getGymSet();
        Set<User> users = task.getUsersResponsibleForTask();

        List<Gym> gymList = new ArrayList<>(gyms);
        List<GymDTO> gymDTOList = new ArrayList<>();
        for(Gym gym : gymList)
            gymDTOList.add(gymMapper.toDTO(gym));

        List<User> userList = new ArrayList<>(users);
        List<UserDTO> userDTOList = new ArrayList<>();
        for(User user : userList)
            userDTOList.add(userMapper.toDTO(user));

        return TaskDTO.builder()
                .taskId(task.getTaskId())
                .category(task.getCategory())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .priority(task.getPriority())
                .subcategory(task.getSubcategory())
                .status(task.getStatus())
                .gyms(gymDTOList)
                .users(userDTOList)
                .build();
    }


    public TaskRequestDTO mapToRequest(Task task)

    {
        TaskDTO taskDTO = mapFrom(task);
        TaskRequestDTO taskRequestDTO = new TaskRequestDTO();
        taskRequestDTO.setTaskDTO(taskDTO);
        Set<User> users = task.getUsersResponsibleForTask();
        Set<Gym> gyms = task.getGymSet();
        for(User user : users)
            System.out.println(user.getName());

        List<User> userList = new ArrayList<>(users);
        List<Gym> gymList = new ArrayList<>(gyms);
        for(User user : userList)
            System.out.println(user.getName());

        List<UserDTO> userDTOList = userMapper.toDTOList(userList);
        List<GymDTO> gymDTOList = gymMapper.toDTOList(gymList);

        taskRequestDTO.setUsers(userDTOList.stream().collect(Collectors.toSet()));
        taskRequestDTO.setGyms(gymDTOList.stream().collect(Collectors.toSet()));


        return taskRequestDTO;
    }
    public List<TaskDTO> taskDTOList(Set<Task> tasks){
        List<TaskDTO> taskList = tasks.stream().map(this::mapFrom).collect(Collectors.toList());
        return taskList;
    }
}
