package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.security.Provider;
import java.util.List;

public interface HorseService {

    /**
     * creates a new entry in the database by calling the respective method in HorseDao
     * @param horse to be created
     * @return the newly created horse
     * @throws ValidationException if the input date is not valid
     * @throws ServiceException if an error occurs while processing the data in lower layers
     */
    Horse createHorse(Horse horse) throws ServiceException, ValidationException;

    /**
     * @return all horses in the database
     * @throws ServiceException if something goes wrong during during data processing.
     */
    List<Horse> getAllHorses() throws ServiceException;

    /**
     * Gets the sport with a given ID.
     *
     * @param id of the sport to find.
     * @return the sport with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the sport could not be found in the system.
     */
    Horse getOneById(Long id);
}
