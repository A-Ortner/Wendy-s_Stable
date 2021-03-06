package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.SportDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.SportMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.SportService;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(SportEndpoint.BASE_URL)
public class SportEndpoint {

    static final String BASE_URL = "/sports";
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SportService sportService;
    private final SportMapper sportMapper;

    @Autowired
    public SportEndpoint(SportService sportService, SportMapper sportMapper) {
        this.sportService = sportService;
        this.sportMapper = sportMapper;
    }

    @GetMapping(value = "/{id}")
    public SportDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return sportMapper.entityToDto(sportService.getOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error("NotFoundException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sport is not in database", e);
        } catch (ServiceException e){
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during reading sport", e);
        }
    }

    @GetMapping
    public List<SportDto> getAllSports() {
        LOGGER.info("GET all sports " + BASE_URL);
        try {
            List<SportDto> sportDtos = new LinkedList<>();
            List<Sport> sports = sportService.getAllSports();
            for (Sport o : sports) {
                sportDtos.add(sportMapper.entityToDto(o));
            }
            return sportDtos;
        } catch (ServiceException e) {
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SportDto createSport(@RequestBody SportDto sportDto){
        LOGGER.info("Post " + BASE_URL);

        try {
            Sport sportToBeCreated = sportMapper.dtoToEntity(sportDto);
            return sportMapper.entityToDto(sportService.createSport(sportToBeCreated));
        }catch(ValidationException e){
            //422: The request was well-formed but was unable to be followed due to semantic errors.
            LOGGER.error("Validationexception " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        }catch (ServiceException e){
            //500: internal server error
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

}
