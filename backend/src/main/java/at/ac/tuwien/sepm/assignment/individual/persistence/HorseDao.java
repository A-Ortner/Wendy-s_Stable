package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.SearchTerms;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

import java.util.List;

public interface HorseDao {

    /**
     * creates a new entry in the database
     * @param horse new entry to be created
     * @return new Horse with newly set id
     * @throws PersistenceException in case an error occurs in the database
     */
    Horse createHorse(Horse horse) throws PersistenceException;

    /**
     * @return all horses in the database
     * @throws PersistenceException in case an error occurs in the database
     */
    List<Horse> getAllHorses() throws PersistenceException;

    /**
     * Get the horse with given ID.
     *
     * @param id of the horse to find.
     * @return the horse with the specified id.
     * @throws NotFoundException   will be thrown if the sport could not be found in the database.
     * @throws PersistenceException in case an error occurs in the database
     */
    Horse getOneById(Long id) throws PersistenceException;

    /**
     * @param horse which is to be updated in the database
     * @return updated Horse
     * @throws NotFoundException will be thrown if the horse could not be found in the database
     * @throws PersistenceException will be thrown if something goes wrong during the database access
     * */
    Horse updateHorse(Horse horse) throws PersistenceException, NotFoundException;

    /**
     * Loads all horses in the db that match each criteria
     *
     * @param searchTerms of the db-query
     * @return List of horses that matches criteria
     * @throws PersistenceException will be thrown if something goes wrong during the data processing
     */
    List<Horse> searchHorses(SearchTerms searchTerms) throws PersistenceException;

    /**
     * Deletes horse and its parent-child-relationships
     * @param id of the horse to be deleted
     * @throws PersistenceException will be thrown if something goes wrong during the data processing
     */
    void deleteHorse(Long id) throws PersistenceException;

    /**
     * Loads the horse with the corresponding id and all its ancestors
     * @param id horse that will be the root
     * @param generations number of generations that will be returned
     * @return List of ancestors and root
     * @throws PersistenceException will be thrown if something goes wrong during the data processing
     */
    List<Horse> getAllAncestors(Long id, Long generations) throws PersistenceException;
}
