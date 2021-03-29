package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.util.Sexes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public abstract class HorseDaoTestBase {

    @Autowired
    HorseDao horseDao;

    @Test
    @DisplayName("Finding horse by non-existing ID should throw NotFoundException")
    public void findingHorseById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> horseDao.getOneById(1L));
    }

    @Test
    @DisplayName("Adding new horse with name.length exceeding 255 char should throw PersistenceException")
    public void addingHorse_nameLengthTooLong_shouldThrowPersistenceException() {
        String name = "Z4a9eVLK5zqj7w33MhKuZdD5qe2fdERdpXnOeF68gna8kyfekrpCrrScIVFicZsK0c97Ep4EXi0n31ZIAkEtMX5RnRxPKVPR68KCDTOQnJQstPdW4OJzDtJqCQgQurXLeB2CgiBZAthQYreYbwGdIRLMj5Y6q1DnvBISOpBL63azq4fYcEpyIHy2INuNWVws8eLUt6phDKB5soqQfiv6DEncI6iS7dLCiJPTNDnePnYh9ptihIUoR8B70OfesRPVlUQxi9DXZxjMxUunf0xvmLZxivFIMKUzbSXb4mYTOdXT";
        LocalDate date = LocalDate.now();
        assertThrows(PersistenceException.class,
            () -> horseDao.createHorse(new Horse(name, Sexes.M, date, "ihaha", null)));
    }

    @Test
    @DisplayName("Adding new horse with valid fields should throw no Exception")
    public void addingHorse_valid_shouldThrowNoException() {
        Horse horse = TestData.getNewMaleHorseWithRequiredFields();

        Horse response =  horseDao.createHorse(horse);
        assertNotNull(response);
    }
}
