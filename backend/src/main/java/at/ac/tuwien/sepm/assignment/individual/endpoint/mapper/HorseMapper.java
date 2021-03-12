package at.ac.tuwien.sepm.assignment.individual.endpoint.mapper;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse){
        if(horse == null) return null;
        return new HorseDto(horse.getId(),
            horse.getName(),
            horse.getSex(),
            horse.getDateOfBirth(),
            horse.getDescription(),
            horse.getFavSportID());
    }

    public Horse dtoToEntity(HorseDto horseDto){
        if(horseDto == null) return null;
        if(horseDto.getFavSportID() == null) horseDto.setFavSportID(-1L);
        return new Horse(horseDto.getId(),
            horseDto.getName(),
            horseDto.getSex(),
            horseDto.getDateOfBirth(),
            horseDto.getDescription(),
            horseDto.getFavSportID());

    }
}
