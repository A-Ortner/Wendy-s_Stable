package at.ac.tuwien.sepm.assignment.individual.unit.persistence;

import static org.junit.jupiter.api.Assertions.*;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Sport;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.PersistenceException;
import at.ac.tuwien.sepm.assignment.individual.persistence.SportDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class SportDaoTestBase {

    @Autowired
    SportDao sportDao;

    @Test
    @DisplayName("Finding sport by non-existing ID should throw NotFoundException")
    public void findingSportById_nonExisting_shouldThrowNotFoundException() {
        assertThrows(NotFoundException.class,
            () -> sportDao.getOneById(1L));
    }

    @Test
    @DisplayName("Adding new sport with name.length exceeding 255 char should throw PersistenceException")
    public void addingSport_nameLengthTooLong_shouldThrowPersistenceException() {
        String name = "Z4a9eVLK5zqj7w33MhKuZdD5qe2fdERdpXnOeF68gna8kyfekrpCrrScIVFicZsK0c97Ep4EXi0n31ZIAkEtMX5RnRxPKVPR68KCDTOQnJQstPdW4OJzDtJqCQgQurXLeB2CgiBZAthQYreYbwGdIRLMj5Y6q1DnvBISOpBL63azq4fYcEpyIHy2INuNWVws8eLUt6phDKB5soqQfiv6DEncI6iS7dLCiJPTNDnePnYh9ptihIUoR8B70OfesRPVlUQxi9DXZxjMxUunf0xvmLZxivFIMKUzbSXb4mYTOdXT";
        assertThrows(PersistenceException.class,
            () -> sportDao.createSport(new Sport(name)));
    }

    @Test
    @DisplayName("Adding new sport with valid fields should throw no exception")
    public void addingSport_nameValid_shouldThrowNoException() {
        Sport sport = TestData.getNewSport("Bouldering");
        Sport response =  sportDao.createSport(sport);
        assertNotNull(response);
        assertEquals(sport.getName(), response.getName());
    }

}
