package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.DTO.DashBoard;
import tradatorii.gym_management.DTO.GymBucket;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Repo.GymRepo;
import tradatorii.gym_management.Service.GymServiceInterface;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor

@Service
public class GymService implements GymServiceInterface {
    private final GymRepo gymRepo;
    private final TaskMapper taskMapper;
    @Override
    public Gym save(Gym gym) {
        return gymRepo.save(gym);
    }

    @Override
    public Gym getGymById(Long id) {
        return gymRepo.getGymByGymId(id);
    }

    @Override
    public List<Gym> getAllGyms() {
        return gymRepo.findAll();
    }

    public void deleteGym(Long id) {
        gymRepo.deleteById(id);
    }

    public List<User> getManagersByGymIds(List<Long> gymIds) {
        return gymRepo.findManagersByGymIds(gymIds);
    }

    @Override

    public GymBucket getBucket(Long gymId) {
        Gym gym = gymRepo.findById(gymId).orElseThrow(() -> new IllegalArgumentException("Gym not found"));
        GymBucket bucket = GymBucket.builder().gymName(gym.getName()).gymId(gym.getGymId()).build();
        Set<Task> tasks = gym.getTaskSet();
        List<TaskDTO> taskDTOS = tasks.stream().map(taskMapper::mapFrom).collect(Collectors.toList());
        bucket.setTasks(taskDTOS);

        bucket.setTotalTasks(gym.getTaskSet().size());

        Set<Task> completedTasks = gym.getTaskSet().stream().filter(task -> task.getStatus().equals(Status.DONE)).collect(Collectors.toSet());
        Set<Task> toDoTasks = gym.getTaskSet().stream().filter(task -> task.getStatus().equals(Status.IN_PROGRESS)).collect(Collectors.toSet());
        Set<Task> backLogTasks = gym.getTaskSet().stream().filter(task -> task.getStatus().equals(Status.BACKLOG)).collect(Collectors.toSet());
        Set<Task> cancelledTasks = gym.getTaskSet().stream().filter(task -> task.getStatus().equals(Status.CANCELLED)).collect(Collectors.toSet());
        Set<Task> inProgressTasks = gym.getTaskSet().stream().filter(task -> task.getStatus().equals(Status.IN_PROGRESS)).collect(Collectors.toSet());

        bucket.setCompletedTasks(completedTasks.size());
        bucket.setToDoTasks(toDoTasks.size());
        bucket.setBacklogTasks(backLogTasks.size());
        bucket.setInProgressTasks(inProgressTasks.size());
        bucket.setCancelledTasks(cancelledTasks.size());

        return bucket;
    }

    @Override
    public DashBoard getDashBoard() {
        List<Gym> gyms = gymRepo.findAll();
        List<GymBucket> gymBuckets = gyms.stream().map(gym -> getBucket(gym.getGymId())).collect(Collectors.toList());
        return DashBoard.builder().bucketList(gymBuckets).totalGyms(gyms.size()).build();
    }


}
