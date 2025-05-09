package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Fetching statistics for gym with ID: {}", gymId);
        Gym gym = gymRepo.findById(gymId).orElseThrow(() -> {
            log.error("Gym with ID {} not found", gymId);
            return new IllegalArgumentException("Gym not found");
        });

        log.debug("Gym found: {}", gym);
        GymStatistics bucket = GymStatistics.builder()
                .gymName(gym.getName())
                .gymId(gym.getGymId())
                .build();

        bucket.setTotalTasks(gym.getTaskSet().size());
        log.debug("Total tasks for gym {}: {}", gymId, bucket.getTotalTasks());

        Map<Status, List<Task>> tasksByStatus = gym.getTaskSet().stream()
                .collect(Collectors.groupingBy(Task::getStatus));

        bucket.setCompletedTasks(tasksByStatus.getOrDefault(Status.DONE, List.of()).size());
        bucket.setToDoTasks(tasksByStatus.getOrDefault(Status.TO_DO, List.of()).size());
        bucket.setBacklogTasks(tasksByStatus.getOrDefault(Status.BACKLOG, List.of()).size());
        bucket.setInProgressTasks(tasksByStatus.getOrDefault(Status.IN_PROGRESS, List.of()).size());
        bucket.setCancelledTasks(tasksByStatus.getOrDefault(Status.CANCELLED, List.of()).size());

        log.debug("Task breakdown for gym {}: Completed: {}, To-Do: {}, Backlog: {}, In-Progress: {}, Cancelled: {}",
                gymId, bucket.getCompletedTasks(), bucket.getToDoTasks(), bucket.getBacklogTasks(),
                bucket.getInProgressTasks(), bucket.getCancelledTasks());

        return bucket;
    }

    @Override
    public DashboardDTO getDashBoard() {
        List<Gym> gyms = gymRepo.findAll();
        log.info("Gyms: {}", gyms);
        List<GymStatistics> gymStatistics = gyms.stream()
                .map(gym -> getGymStatistics(gym.getGymId())).collect(Collectors.toList());
        log.info("Gym Statistics: {}", gymStatistics);
        return DashboardDTO.builder().allGymsStatistics(gymStatistics).totalGyms(gyms.size()).build();
    }

    @Override
    public GymDTO getGymByUserId(Long userId) {
        User existingUser = userService.getById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Gym gym = gymRepo.findGymByManager(existingUser);

        return gymMapper.toDTO(gym);
    }


}
