package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.SearchTerms;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseDao horseDao;
    private final Validator validator;

    @Autowired
    public HorseServiceImpl(HorseDao horseDao, Validator validator) {
        this.horseDao = horseDao;
        this.validator = validator;
    }

    @Override
    public Horse createHorse(Horse horse) throws ServiceException, ValidationException {
        LOGGER.trace("createHorse({})", horse.toString());

        validator.validateNewHorse(horse);

        try {
            return horseDao.createHorse(horse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Horse> getAllHorses() throws ServiceException {
        LOGGER.info("getAllHorses()"); //todo: trace
        try {
            return horseDao.getAllHorses();
        }catch (PersistenceException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.trace("getOneById({})", id);
        return horseDao.getOneById(id);
    }

    @Override
    public Horse updateHorse(Horse horse) throws ServiceException, NotFoundException, ValidationException {
        LOGGER.info("updateHorse({})", horse.toString()); //todo: trace

        validator.validateUpdatedHorse(horse);

        try {
            return horseDao.updateHorse(horse);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Horse> searchHorses(SearchTerms searchTerms) throws ServiceException {
        try {
            return horseDao.searchHorses(searchTerms);
        } catch (PersistenceException e) {
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteHorse(Long id) throws ServiceException {

        //check if id valid
        try {
            horseDao.getOneById(id);
        }catch (NotFoundException e){
            LOGGER.error("Delete horse: HorseId is not in the database.");
            throw new ServiceException("Horse is not in the database. Deletion denied.");
        }

        deleteParentChildRelations(id);

        try {
            this.horseDao.deleteHorse(id);
        }catch (PersistenceException e){
            throw new ServiceException(e.getMessage(), e.getCause());
        }
    }

    private void deleteParentChildRelations(Long id) {
        List<Horse> horses;
        try {
           horses = horseDao.getAllHorses();
        }catch (Exception e){
            LOGGER.error("Delete horse: Children could not be loaded.");
            throw new ServiceException("Could not load children. Deletion denied.");
        }

        for (Horse horse: horses) {
            boolean changed = false;

            if(horse.getParent1Id() == id){
                horse.setParent1Id(null);
                changed = true;
            }
            if(horse.getParent2Id() == id){
                horse.setParent2Id(null);
                changed = true;
            }

            if(changed){
                LOGGER.info("Updated parent-child relation for horse with id " + horse.getId()); //todo: trace
                horseDao.updateHorse(horse);
            }
        }
    }

}
