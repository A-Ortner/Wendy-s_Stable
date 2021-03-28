package at.ac.tuwien.sepm.assignment.individual.endpoint;

import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.TreeHorseDto;
import at.ac.tuwien.sepm.assignment.individual.endpoint.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.SearchTerms;
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
import java.util.Arrays;
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
            LOGGER.error("NotFoundException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horse not found.", e);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during loading horse from database.");
        } catch (ValidationException e) {
            LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validating request: " + e.getMessage());
        }
    }

    @GetMapping()
    public List<HorseDto> getAllHorses(@RequestParam(required = false, name = "name") String name,
                                       @RequestParam(required = false, name = "sex") String sex,
                                       @RequestParam(required = false, name = "dateOfBirth") String dateOfBirth,
                                       @RequestParam(required = false, name = "description") String description,
                                       @RequestParam(required = false, name = "favSportId") Long favSportId) {

        if (name != null || sex != null || dateOfBirth != null || description != null || favSportId != null) {
            LOGGER.info("GET all horses " + BASE_URL + "with param " + name + " " + sex + " " + dateOfBirth + " " + description + " " + favSportId);
            SearchTerms st = new SearchTerms(name, dateOfBirth, sex, description, favSportId);

            try {
                List<HorseDto> horseDtos = new LinkedList<>();
                List<Horse> horses = horseService.searchHorses(st);
                for (Horse o : horses) {
                    horseDtos.add(horseMapper.entityToDto(o));
                }
                return horseDtos;
            } catch (ServiceException e) {
                LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
            } catch (ValidationException e) {
                LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validating request: " + e.getMessage());
            }

        } else {
            LOGGER.info("GET all horses " + BASE_URL);
            try {
                List<HorseDto> horseDtos = new LinkedList<>();
                List<Horse> horses = horseService.getAllHorses();
                for (Horse o : horses) {
                    horseDtos.add(horseMapper.entityToDto(o));
                }
                return horseDtos;
            } catch (ServiceException e) {
                LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
            }
        }

    }

    @GetMapping(value = "/treehorses")
    @ResponseStatus(HttpStatus.OK)
    public List<TreeHorseDto> getAllTreeHorses() {
        LOGGER.info("GET all TreeHorses " + BASE_URL + "/treehorses");
        try {
            List<TreeHorseDto> treeHorseDtos = new LinkedList<>();
            List<Horse> horses = horseService.getAllHorses();
            for (Horse o : horses) {
                treeHorseDtos.add(horseMapper.entityToTreeDto(o));
            }
            return treeHorseDtos;
        } catch (NotFoundException e) {
            LOGGER.error("NotFoundException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horse not found.", e);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @GetMapping(value = "/ancestors/{id}/{generations}")
    @ResponseStatus(HttpStatus.OK)
    public List<TreeHorseDto> getAllAncestors(@PathVariable("id") Long id,
                                              @PathVariable("generations") Long generations) {
        LOGGER.info("GET all ancestors " + BASE_URL + "/ancestors/" + id);
        try {
            List<TreeHorseDto> treeHorseDtos = new LinkedList<>();
            List<Horse> horses = horseService.getAllAncestors(id, generations);
            for (Horse o : horses) {
                treeHorseDtos.add(horseMapper.entityToTreeDto(o));
            }
            return treeHorseDtos;
        } catch (NotFoundException e) {
            LOGGER.error("NotFoundException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horse not found.", e);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (ValidationException e) {
            LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validating request: " + e.getMessage());
        }
    }

    @GetMapping(value = "/fullhorses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<HorseDto> getFullHorse(@PathVariable("id") Long id) {
        LOGGER.info("GET full horse " + BASE_URL + "/fullhorses/" + id);
        try {
            List<HorseDto> horseDtos = new LinkedList<>();
            List<Horse> horses = horseService.getAllAncestors(id, 2L);
            for (Horse o : horses) {
                horseDtos.add(horseMapper.entityToDto(o));
            }
            return horseDtos;

        } catch (NotFoundException e) {
            LOGGER.error("NotFoundException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Horse not found.", e);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        } catch (ValidationException e) {
            LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validating request: " + e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto createHorse(@RequestBody HorseDto horseDto) {
        LOGGER.info("Post " + BASE_URL);
        try {
            Horse HorseToBeCreated = horseMapper.dtoToEntity(horseDto);
            return horseMapper.entityToDto(horseService.createHorse(HorseToBeCreated));
        } catch (ValidationException e) {
            //422: The request was well-formed but was unable to be followed due to semantic errors.
            LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ServiceException e) {
            //500: internal server error
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public HorseDto updateHorse(@RequestBody HorseDto horse) {
        LOGGER.info("Put " + BASE_URL + "/{}", horse.getId());

        try {
            Horse horseEntity = horseMapper.dtoToEntity(horse);
            return horseMapper.entityToDto(horseService.updateHorse(horseEntity));
        } catch (ValidationException e) {
            //422: The request was well-formed but was unable to be followed due to semantic errors.
            LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage());
        } catch (ServiceException e) {
            //500: internal server error
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteHorse(@PathVariable("id") Long id) {
        LOGGER.info("DELETE " + BASE_URL + "/{}", id);
        try {
            this.horseService.deleteHorse(id);
        } catch (ServiceException e) {
            LOGGER.error("ServiceException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error during deleting horse", e);
        } catch (ValidationException e) {
            LOGGER.error("ValidationException " + Arrays.toString(e.getStackTrace()));
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during validating request: " + e.getMessage());
        }
    }

}
