package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.security.Provider;

public interface HorseService {

    /**
     * creates a new entry in the database by calling the respective method in HorseDao
     * @param horse to be created
     * @return the newly created horse
     * @throws ValidationException if the input date is not valid
     * @throws ServiceException if an error occurs while processing the data in lower layers
     */
    Horse createHorse(Horse horse) throws ServiceException, ValidationException;
}
