package tradatorii.gym_management.Mappers;

import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;

import java.util.ArrayList;
import java.util.List;

@Component
public class GymMapper {
    public  GymDTO toDTO(Gym gym){
        return GymDTO.builder()
                .id(gym.getGymId())
                .name(gym.getName())
                .address(gym.getAddress())
                .build();
    }

    public  Gym fromDTO(GymDTO gymDTO){
        return Gym.builder()
                .gymId(gymDTO.getId())
                .name(gymDTO.getName())
                .address(gymDTO.getAddress())
                .build();
    }

    public List<GymDTO> toDTOList(List<Gym> gyms){
        List<GymDTO> gymDTOS = new ArrayList<>();
        for (Gym gym : gyms)
        {
            GymDTO gymDTO = toDTO(gym);
            gymDTOS.add(gymDTO);
        }
        return gymDTOS;
    }
}
