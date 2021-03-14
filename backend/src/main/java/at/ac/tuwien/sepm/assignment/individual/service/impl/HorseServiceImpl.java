package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
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

}
