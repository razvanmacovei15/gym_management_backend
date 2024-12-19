package tradatorii.gym_management.Mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tradatorii.gym_management.DTO.GymDTO;
import tradatorii.gym_management.Entity.Gym;
import tradatorii.gym_management.Entity.User;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GymMapper {
    private final UserMapper userMapper;
    public  GymDTO toDTO(Gym gym){
        return GymDTO.builder()
                .id(gym.getGymId())
                .name(gym.getName())
                .address(gym.getAddress())
                .managerId(gym.getManager().getUserId())
                .build();
    }

    public  Gym fromDTO(GymDTO gymDTO, User manager){
        return Gym.builder()
                .gymId(gymDTO.getId())
                .name(gymDTO.getName())
                .address(gymDTO.getAddress())
                .manager(manager)
                .build();
    }

    public List<GymDTO> toDTOList(List<Gym> gyms){
        List<GymDTO> gymDTOs = new ArrayList<>();
        for (Gym gym : gyms)
        {
            GymDTO gymDTO = toDTO(gym);
            gymDTOs.add(gymDTO);
        }
        return gymDTOs;
    }
}
