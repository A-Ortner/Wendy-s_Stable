package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.SearchTerms;
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
     * @throws ServiceException if something goes wrong during during data processing.
     */
    Horse getOneById(Long id) throws ServiceException;

    /**
     * updates values in db for horse with the specified id
     *
     * @param horse which is to be updated in the database
     * @return updated Horse
     * @throws ServiceException will be thrown if something goes wrong during the data processing
     * @throws NotFoundException will be thrown if the horse could not be found in the database
     * @throws ValidationException will be thrown if input fields are invalid during data processing
     * */
    Horse updateHorse(Horse horse) throws ServiceException, NotFoundException, ValidationException;

    /**
     * Loads all horses in the db that match each criteria
     *
     * @param searchTerms of which a query will be built
     * @return List of horses that matches criteria
     * @throws ServiceException will be thrown if something goes wrong during the data processing
     */
    List<Horse> searchHorses(SearchTerms searchTerms) throws ServiceException;

    /**
     * Deletes horse and its parent-child-relationships
     * @param id of the horse to be deleted
     * @throws ServiceException  will be thrown if something goes wrong during the data processing
     */
    void deleteHorse(Long id) throws ServiceException;

    /**
     * Loads the horse with the corresponding id and all its ancestors
     * @param id horse that will be the root
     * @param generations max. number of generations that will be loaded
     * @return List of ancestors and root
     * @throws ServiceException will be thrown if something goes wrong during the data processing
     * @throws NotFoundException if the sport could not be found in the system.
     * @throws ValidationException will be thrown if input fields are invalid during data processing
     */
    List<Horse> getAllAncestors(Long id, Long generations) throws ServiceException, NotFoundException, ValidationException;
}
