package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;

import java.util.List;

public interface SportDao {

    /**
     * Get the sport with given ID.
     *
     * @param id of the sport to find.
     * @return the sport with the specified id.
     * @throws PersistenceException will be thrown if something goes wrong while accessing the persistent data store.
     * @throws NotFoundException   will be thrown if the sport could not be found in the database.
     */
    Sport getOneById(Long id);

    /**
     * @return all horses in the database
     * @throws PersistenceException if something goes wrong during during data processing.
     */
    List<Sport> getAllSports() throws PersistenceException;
}
