package tradatorii.gym_management.Service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.DTO.TaskDTO;
import tradatorii.gym_management.DTO.TaskRequestDTO;
import tradatorii.gym_management.DTO.UserDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.Task;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Category;
import tradatorii.gym_management.Enums.Status;
import tradatorii.gym_management.Mappers.GymMapper;
import tradatorii.gym_management.Mappers.UserMapper;
import tradatorii.gym_management.Repo.GymRepo;
import tradatorii.gym_management.Repo.TaskRepo;
import tradatorii.gym_management.Repo.UserRepo;
import tradatorii.gym_management.Service.GymServiceInterface;
import tradatorii.gym_management.Service.TaskServiceInterface;
import tradatorii.gym_management.Service.UserServiceInterface;
import tradatorii.gym_management.minio.MinioService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService implements TaskServiceInterface {

    //just to fill the task repo

    private static final String[] PROBLEM_TITLES = {
            "Treadmill malfunction",
            "Bench press maintenance",
            "Elliptical machine repair",
            "Stationary bike issue",
            "Weight rack adjustment",
            "Broken dumbbell replacement",
            "Locker room cleaning",
            "Air conditioning malfunction",
            "Water dispenser refill",
            "Lighting issue in cardio area",
            "Floor mat replacement",
            "Shower drain clog",
            "Sauna temperature issue",
            "Music system repair",
            "Broken mirror in weight area",
            "Gym entrance door repair",
            "First aid kit replenishment",
            "Emergency exit sign replacement",
            "Yoga mat cleaning",
            "Power outage in gym"
    };
    private static final String[] PROBLEM_DESCRIPTIONS = {
            "The treadmill is either not powering on, the belt is slipping, or the speed controls are unresponsive.",
            "The bench press has loose components or needs lubrication, tightening, or alignment to ensure safe usage.",
            "The elliptical machine is making unusual noises, has resistance issues, or displays an error message.",
            "The stationary bike has a faulty pedal, resistance adjustment problem, or broken display panel.",
            "The weight rack is unstable or improperly aligned, posing a safety risk and requiring adjustment.",
            "One or more dumbbells are damaged, with loose weights or cracked handles, and need immediate replacement.",
            "The locker room requires thorough cleaning to address unpleasant odors, spills, and maintain hygiene standards.",
            "The gym's air conditioning system is not functioning properly, causing discomfort due to insufficient cooling.",
            "The water dispenser is empty and requires immediate refill to keep members hydrated during workouts.",
            "Lights in the cardio section are flickering, dim, or completely out, requiring maintenance to improve visibility.",
            "Floor mats are torn, worn out, or slipping, needing replacement to ensure safety and comfort during exercises.",
            "Water is pooling in the shower area due to a clogged drain, causing inconvenience and potential hygiene issues.",
            "The sauna is either too hot or not heating up enough, requiring adjustments to the temperature controls.",
            "The gym’s music system is not functioning, with issues like distorted sound, non-responsive controls, or connectivity problems.",
            "A mirror in the weightlifting section is cracked or shattered, posing a safety hazard and needing replacement.",
            "The main entrance door is not closing or locking properly, creating a security issue that needs immediate attention.",
            "The first aid kit is missing essential items like bandages or antiseptics and needs restocking for safety compliance.",
            "The emergency exit sign is missing, damaged, or not illuminated, posing a safety risk in case of an evacuation.",
            "The yoga mats are dirty or smelly and need a thorough cleaning to maintain hygiene for users.",
            "The gym is experiencing a power outage, affecting equipment, lighting, and climate control."
    };


    //just to fill the task repo

    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final UserServiceInterface userService;
    private final GymRepo gymRepo;
    private final GymMapper gymMapper;
    private final GymServiceInterface gymService;
    private final MinioService minioService;

    @Override
    public Task save(Task task) {
        return taskRepo.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public List<Task> findAllOrderByCreatedAtDesc() {
        return taskRepo.findAllByOrderByCreatedAtDesc();
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

        // Save and return the task
        return taskRepo.save(task);
    }
    @Override
    public void openTheGates() {
        System.out.println("Opening the gates");
        List<Gym> gyms = gymService.getAllGyms();
        List<User> managers = userService.getAllManagers();
        List<Category> categories = Arrays.asList(Category.values());
        List<Status> statuses = Arrays.asList(Status.values());
        List<String> priorities = Arrays.asList("HIGH", "LOW");
        Random random = new Random();

        Status status = statuses.get(random.nextInt(statuses.size()));

        for(User user : managers){

            for (int i = 0; i < PROBLEM_TITLES.length; i++) {

                List<User> randomUserPool = new ArrayList<>(managers);
                randomUserPool.remove(user);

                List<User> usersToAdd = new ArrayList<>();

                for(int j = 0; j < random.nextInt(3) + 1; j++) {
                    if (!randomUserPool.isEmpty()) {
                        User randomUser = randomUserPool.get(new Random().nextInt(randomUserPool.size()));
                        usersToAdd.add(randomUser);
                    }
                }

                List<Gym> gymsToAdd = new ArrayList<>();

                for(int j = 0; j < random.nextInt(3) + 1; j++) {
                    if (!gyms.isEmpty()) {
                        Gym randomGym = gyms.get(new Random().nextInt(gyms.size()));
                        gymsToAdd.add(randomGym);
                    }
                }

                Status randomStatus = statuses.get(random.nextInt(statuses.size()));

                List<UserDTO> userDTOs = usersToAdd.stream()
                        .map(userMapper::toDTO)
                        .collect(Collectors.toList());

                List<GymDTO> gymDTOs = gymsToAdd.stream()
                        .map(gymMapper::toDTO)
                        .collect(Collectors.toList());

                TaskDTO dto = TaskDTO.builder()
                        .title(PROBLEM_TITLES[i])
                        .category(categories.get(random.nextInt(categories.size())))
                        .description(PROBLEM_DESCRIPTIONS[i])
                        .deadline(LocalDate.now().plusDays(random.nextInt(7)).toString())
                        .priority(priorities.get(random.nextInt(priorities.size())))
                        .status(randomStatus)
                        .users(userDTOs)
                        .gyms(gymDTOs)
                        .build();

                Task task = this.createNewTask(TaskRequestDTO.builder()
                        .taskDTO(dto)
                        .users(new HashSet<>(userDTOs))
                        .gyms(new HashSet<>(gymDTOs))
                        .build());

                String taskBucket = this.createTaskBucket(task);
                task.setTaskBucket(taskBucket);

            }
        }



    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepo.findById(id).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }

    @Override
    public String createTaskBucket(Task task) {
        String bucketName = "task-" + task.getTaskId() + "-bucket";
        if(minioService.bucketExists(bucketName)) {
            return bucketName;
        }
        return minioService.createBucket(bucketName);
    }

    @Override
    public String getTaskBucket(Task task) {
        String bucketName = "task-" + task.getTaskId() + "-bucket";
        if(minioService.bucketExists(bucketName)) {
            return bucketName;
        }
        return minioService.createBucket(bucketName);
    }

    @Override
    public List<Task> getTasksByGym(Gym gym) {
        return taskRepo.findAllTasksByGym(gym);
    }

    public List<Task> getTasksByManagerUserId(Long userId) {
        return taskRepo.findAllTasksByManagerUserId(userId);
    }

}
