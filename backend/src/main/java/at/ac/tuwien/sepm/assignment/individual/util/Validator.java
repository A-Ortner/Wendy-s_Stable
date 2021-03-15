package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    SportDao sportDao;

    public Validator(SportDao sportDao) {
        this.sportDao = sportDao;
    }

    public void validateNewSport(Sport sport) {
        if(sport.getName()==null) throw new ValidationException("Name is not set.");
    }

    public void validateNewHorse(Horse horse) throws ValidationException {
        //todo: if id: check if it already exists (for update)

        if (horse.getName() == null || horse.getName().isBlank()){
            LOGGER.error("Horse´s name is null.");
            throw new ValidationException("name is not set.");
        }

        if(horse.getSex() == null){
            LOGGER.error("Horse´s sex is null.");
            throw new ValidationException("sex is not set.");
        }

        if(horse.getDateOfBirth() == null){
            LOGGER.error("Horse´s date of birth is null.");
            throw new ValidationException("dateOfbirth is not set.");
        }

        LocalDate now = LocalDate.now();
        if((horse.getDateOfBirth().compareTo(now)) > 0){
            LOGGER.error("Horse´s date of birth is in the future");
            throw new ValidationException("dateOfbirth is in the future.");
        }

        if((horse.getDescription() != null) && horse.getDescription().length() > 2000 -1){
            LOGGER.error("Horse´s description is too long");
            throw new ValidationException("description too long");
        }

        if(horse.getFavSportId() != null){
            try {
                sportDao.getOneById(horse.getFavSportId());
            }catch (NotFoundException e){
                //should never happen because sport is chosen via enum
                LOGGER.error("Horse´s sport is not in the database.");
                throw new ValidationException("sport not in database");
            }
        }
    }

}
