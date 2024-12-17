package tradatorii.gym_management.Controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;
import tradatorii.gym_management.Enums.Role;
import tradatorii.gym_management.Mappers.GymMapper;
import tradatorii.gym_management.Repo.GymRepo;
import tradatorii.gym_management.Repo.UserRepo;
import tradatorii.gym_management.Service.GymServiceInterface;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gyms")
@AllArgsConstructor
public class GymController {
    private final GymServiceInterface gymService;

    private final GymMapper gymMapper;
    @Autowired
    private final GymRepo gymRepository;
    @Autowired
    private final UserRepo userRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createGym(@RequestBody GymDTO gymDTO) {
        // Find manager by ID
        User manager = userRepository.findById(gymDTO.getManagerId())
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));

        // Validate manager role
        if (!Role.MANAGER.equals(manager.getRole())) {
            throw new IllegalArgumentException("User must have the 'manager' role.");
        }

        // Map DTO to Gym entity and save
        Gym gym = gymMapper.fromDTO(gymDTO, manager);
        gymRepository.save(gym);

        return ResponseEntity.ok("Gym created successfully with manager ID: " + manager.getUserId());
    }

    @GetMapping("/all")
    public ResponseEntity<List<GymDTO>> getAllGyms() {
        List<Gym> gyms = gymService.getAllGyms();
        List<GymDTO> gymDTOs = gyms.stream().map(gymMapper::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(gymDTOs);
    }

}
