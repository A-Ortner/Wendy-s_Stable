package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;

import java.util.List;

public interface SportService {


    /**
     * Gets the sport with a given ID.
     *
     * @param id of the sport to find.
     * @return the sport with the specified id.
     * @throws RuntimeException  if something goes wrong during data processing.
     * @throws NotFoundException if the sport could not be found in the system.
     */
    Sport getOneById(Long id);

    /**
     * @return all sports in the database
     * @throws ServiceException if something goes wrong during during data processing.
     */
    List<Sport> getAllSports() throws ServiceException;


    /**
     * creates a new entry in the database by calling the respective method in SportDao
     * @param sport to be created
     * @return the newly created sport
     * @throws ValidationException if the input date is not valid
     * @throws ServiceException if an error occurs while processing the data in lower layers
     */
    Sport createSport(Sport sport) throws ValidationException, ServiceException;
}
