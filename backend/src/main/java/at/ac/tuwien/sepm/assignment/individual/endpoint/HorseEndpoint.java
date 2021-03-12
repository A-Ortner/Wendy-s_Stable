package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.security.Provider;

@RestController
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {

    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final HorseService horseService;
    private final HorseMapper horseMapper;

    @Autowired
    public HorseEndpoint(HorseService horseService, HorseMapper horseMapper) {
        this.horseService = horseService;
        this.horseMapper = horseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createHorse(@RequestBody HorseDto horseDto){
        LOGGER.info("Post " + BASE_URL);
        LOGGER.info(horseDto.toString()); //todo: change to .debug later
        try {
            Horse HorseToBeCreated = horseMapper.dtoToEntity(horseDto);
            return horseMapper.entityToDto(horseService.createHorse(HorseToBeCreated));
        }catch(ValidationException e){
            //422: The request was well-formed but was unable to be followed due to semantic errors.
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }catch (ServiceException e){
            //500: internal server error
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }

    }


}
