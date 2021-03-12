package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;

public interface HorseDao {

    /**
     * creates a new entry in the database
     * @param horse new entry to be created
     * @return new Horse with newly set id
     * @throws PersistenceException in case an error occurs in the database
     */
    Horse createHorse(Horse horse) throws PersistenceException;
}
