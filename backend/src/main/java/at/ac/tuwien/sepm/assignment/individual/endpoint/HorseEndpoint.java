package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
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
import java.util.LinkedList;
import java.util.List;

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

    @GetMapping(value = "/{id}")
    public HorseDto getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return horseMapper.entityToDto(horseService.getOneById(id));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading sport", e);
        }
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

    @GetMapping()
    public List<HorseDto> getAllHorses(){
        LOGGER.info("GET all horses " + BASE_URL);
        try {
            List<HorseDto> horseDtos = new LinkedList<>();
            List<Horse> horses = horseService.getAllHorses();
            for (Horse o : horses) {
                horseDtos.add(horseMapper.entityToDto(o));
            }
            return horseDtos;
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto updateHorse(@RequestBody HorseDto horse) {
        LOGGER.info("Put " + BASE_URL + "/{}", horse.getId());
        LOGGER.info(horse.toString()); //todo: remove

        try {
            Horse horseEntity = horseMapper.dtoToEntity(horse);
            return horseMapper.entityToDto(horseService.updateHorse(horseEntity));
        } catch (ValidationException | ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during updating horse with name " + horse.getName()+ ": " + e.getMessage(), e);
        }
    }


}
