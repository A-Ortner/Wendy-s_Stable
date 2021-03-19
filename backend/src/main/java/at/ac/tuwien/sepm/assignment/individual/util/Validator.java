package at.ac.tuwien.sepm.assignment.individual.util;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Validator {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    SportDao sportDao;
    HorseDao horseDao;

    public Validator(SportDao sportDao, HorseDao horseDao) {
        this.sportDao = sportDao;
        this.horseDao = horseDao;
    }

    public void validateNewSport(Sport sport) {
        if(sport.getName()==null) throw new ValidationException("Name is not set.");
        if((sport.getDescription() !=null)&&(sport.getDescription().length() > 2000 -1)){
            throw new ValidationException("Description is too long.");
        }
    }

    public void validateNewHorse(Horse horse) throws ValidationException {
        //todo: if id: check if it already exists (for update)

        if (horse.getName() == null || horse.getName().isBlank()){
            LOGGER.error("Horse´s name is null for horse with id " + horse.getId());
            throw new ValidationException("name is not set.");
        }

        if(horse.getSex() == null){
            LOGGER.error("Horse´s sex is null for horse with id " + horse.getId());
            throw new ValidationException("sex is not set.");
        }

        if(horse.getDateOfBirth() == null){
            LOGGER.error("Horse´s date of birth is null for horse with id " + horse.getId());
            throw new ValidationException("dateOfbirth is not set.");
        }

        LocalDate now = LocalDate.now();
        if((horse.getDateOfBirth().compareTo(now)) > 0){
            LOGGER.error("Horse´s date of birth is in the future for horse with id " + horse.getId());
            throw new ValidationException("dateOfbirth is in the future.");
        }

        if((horse.getDescription() != null) && horse.getDescription().length() > 2000 -1){
            LOGGER.error("Horse´s description is too long for horse with id " + horse.getId());
            throw new ValidationException("description too long");
        }

        if(horse.getFavSportId() != null){
            try {
                sportDao.getOneById(horse.getFavSportId());
            }catch (NotFoundException e){
                //should never happen because sport is chosen via enum
                LOGGER.error("Horse´s sport is not in the database for horse with id " + horse.getId());
                throw new ValidationException("sport not in database");
            }
        }

        //validate parents
        validateParentFields(horse);

    }

    private void validateParentFields(Horse horse) {
        if(horse.getParent1Id() != null){
            Horse parent1;
            try {
                parent1 = horseDao.getOneById(horse.getParent1Id());
            }catch (NotFoundException e){
                //should never happen because parent is chosen via enum
                LOGGER.error("Horse´s parent1 is not in the database for horse with id " + horse.getId());
                throw new ValidationException("parent1 not in database");
            }

            //check whether date in future
            if(horse.getDateOfBirth().compareTo(parent1.getDateOfBirth())<0){
                LOGGER.error("Parent1 is younger than current horse for horse with id " + horse.getId());
                throw new ValidationException("Parent1 cannot be younger than the current horse.");

            }
        }

        if(horse.getParent2Id() != null){
            Horse parent2;
            try {
                parent2 = horseDao.getOneById(horse.getParent2Id());
            }catch (NotFoundException e){
                //should never happen because parent is chosen via enum
                LOGGER.error("Horse´s parent2 is not in the database for horse with id " + horse.getId());
                throw new ValidationException("parent2 not in database");
            }

            //check whether date in future
            if(horse.getDateOfBirth().compareTo(parent2.getDateOfBirth())<0){
                LOGGER.error("Parent2 is younger than current horse for horse with id " + horse.getId());
                throw new ValidationException("Parent2 cannot be younger than the current horse.");

            }
        }

        if((horse.getParent1Id()!= null)&&(horse.getParent2Id() != null)){
            Horse p1,p2;
            try {
                p1 = horseDao.getOneById(horse.getParent1Id());
                p2 = horseDao.getOneById(horse.getParent2Id());
            }catch (NotFoundException e){
                LOGGER.error("Parent1 | Parent2 is not in datebase for horse with id " + horse.getId());
                throw new ValidationException("At least one parent is not in the database.");
            }

            if(p1.getSex() == p2.getSex()){
                LOGGER.error("Parents are of the same sex for horse with id " + horse.getId());
                throw new ValidationException("Horse´s parents cannot be of the same sex.");
            }


        }
    }

    public void validateUpdatedHorse(Horse horse) {
        this.validateNewHorse(horse);

        //id has to be valid
        if(horse.getId()==null){
            LOGGER.error("Update horse: HorseId is not in the database.");
            throw new ValidationException("Horse is not in the database. Update denied.");
        }

        //horses sex cannot be changed if it is enlisted as parent
        if(horse.getId()!=null){
            Horse old = horseDao.getOneById(horse.getId());
            if(!old.getSex().equals(horse.getSex())){
                List<Horse> horses = horseDao.getAllHorses();
                for (Horse h : horses) {
                    if((h.getParent1Id().equals(horse.getId())) || (h.getParent2Id().equals(horse.getId()))){
                        LOGGER.error("Horse´s is already defined as parent and sex cannot be changed.");
                        throw new ValidationException("Horse´s sex cannot be changed if it is already enlisted as parent.");
                    }
                }
            }
        }
    }
}
