package at.ac.tuwien.sepm.assignment.individual.base;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.util.Sexes;

import java.time.LocalDate;

public interface TestData {

    /**
     * URI Data
     */
    String BASE_URL = "http://localhost:";
    String HORSE_URL = "/horses";
    String SPORT_URL = "/sports";

    /**
     * Sport Data
     */
    static Sport getNewSport() {
        return new Sport("Sport");
    }

    static Sport getNewSport(String name) {
        return new Sport(name);
    }

    static Sport getNewSportWithId() {
        return new Sport(1L, "Sport");
    }

    static Horse getNewMaleHorseWithRequiredFields(){
        LocalDate date = LocalDate.now();
        return new Horse(1L, "Sarutobi", Sexes.M, date, null, null, null,null );
    }


    static HorseDto getNewHorseDtoWithNameNull() {
        LocalDate date = LocalDate.now();
        return new HorseDto(0L,null, Sexes.M, date, null, null, null,null);
    }

    static HorseDto getNewMaleHorseDtoWithRequiredFields() {
        LocalDate date = LocalDate.now();
        return new HorseDto(0L,"Iruka", Sexes.M, date, null, null, null,null);
    }

    static HorseDto getNewHorseDtoWithValidFields() {
        LocalDate date = LocalDate.now();
        return new HorseDto(1L, "Sarutobi", Sexes.M, date, null, null, null,null );
    }

    static HorseDto getNewHorseDtoWithInvalidId() {
        LocalDate date = LocalDate.now();
        return new HorseDto(0L, "Sarutobi", Sexes.M, date, null, null, null,null );
    }
}
