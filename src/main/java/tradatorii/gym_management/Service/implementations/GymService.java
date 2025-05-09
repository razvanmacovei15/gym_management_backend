package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.DTO.DashboardDTO;
import tradatorii.gym_management.DTO.GymStatistics;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Mappers.GymMapper;
import tradatorii.gym_management.Mappers.TaskMapper;
import tradatorii.gym_management.Repo.GymRepo;
import tradatorii.gym_management.Service.GymServiceInterface;
import tradatorii.gym_management.Service.UserServiceInterface;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;


@RequiredArgsConstructor

@Service
public class GymService implements GymServiceInterface {
    private final GymRepo gymRepo;
    private final UserServiceInterface userService;
    private final GymMapper gymMapper;
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

    public GymStatistics getGymStatistics(Long gymId) {
        Gym gym = gymRepo.findById(gymId).orElseThrow(() -> new IllegalArgumentException("Gym not found"));
        GymStatistics bucket = GymStatistics.builder().gymName(gym.getName()).gymId(gym.getGymId()).build();
        bucket.setTotalTasks(gym.getTaskSet().size());
        Map<Status, List<Task>> tasksByStatus = gym.getTaskSet().stream().collect(Collectors.groupingBy(Task::getStatus));

        bucket.setCompletedTasks(tasksByStatus.get(Status.DONE).size());
        bucket.setToDoTasks(tasksByStatus.get(Status.TO_DO).size());
        bucket.setBacklogTasks(tasksByStatus.get(Status.BACKLOG).size());
        bucket.setInProgressTasks(tasksByStatus.get(Status.IN_PROGRESS).size());
        bucket.setCancelledTasks(tasksByStatus.get(Status.CANCELLED).size());

        return bucket;
    }

    @Override
    public DashboardDTO getDashBoard() {
        List<Gym> gyms = gymRepo.findAll();
        List<GymStatistics> gymStatistics = gyms.stream()
                .map(gym -> getGymStatistics(gym.getGymId())).collect(Collectors.toList());
        return DashboardDTO.builder().allGymsStatistics(gymStatistics).totalGyms(gyms.size()).build();
    }

    @Override
    public GymDTO getGymByUserId(Long userId) {
        User existingUser = userService.getById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Gym gym = gymRepo.findGymByManager(existingUser);

        return gymMapper.toDTO(gym);
    }


}
