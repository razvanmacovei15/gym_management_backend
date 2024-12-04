package tradatorii.gym_management.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Mappers.GymMapper;
import tradatorii.gym_management.Service.GymServiceInterface;

@RestController
@RequestMapping("/gyms")
@AllArgsConstructor
public class GymController {
    private final GymServiceInterface gymService;

    private final GymMapper gymMapper;

    @PostMapping("/create")
    public ResponseEntity<GymDTO> saveGym(@RequestBody GymDTO gymDTO) {
        Gym gym = gymMapper.fromDTO(gymDTO);
        Gym savedGym = gymService.save(gym);
        GymDTO savedGymDTO = gymMapper.toDTO(savedGym);
        return ResponseEntity.ok(savedGymDTO);
    }

}
