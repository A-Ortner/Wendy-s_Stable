package at.ac.tuwien.sepm.assignment.individual.unit.service;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;

import at.ac.tuwien.sepm.assignment.individual.util.Sexes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseServiceTestBase {

    @Autowired
    HorseService horseService;

    @Test
    @DisplayName("Creating a horse-object where name is null should throw Validationexception.")
    public void creatingHorse_nameNull_shouldThrowValidationException() throws ValidationException {
        Horse horse = TestData.getNewMaleHorseWithRequiredFields();
        horse.setName(null);
        assertThrows(ValidationException.class,
            () -> horseService.createHorse(horse));
    }

    // test update horse so that same sex parents
    @Test
    @DisplayName("Updating a horse´s sex when it is already a parent should throw ValidationException")
    public void givenParentHorse_whenUpdatingSex_ThenValidationException() {
        //horse from testdata: male, is parent of horse -6 and -9
        Horse parent = horseService.getOneById(-3L);
        assert (parent.getSex() == Sexes.M);
        parent.setSex(Sexes.F);
        assertThrows(ValidationException.class,
            () -> horseService.updateHorse(parent));
    }


    @Test
    @DisplayName("Deleting a horse by non-existing ID should throw ValidationException")
    public void givenHorseNotInTheDatabase_whenDelete_ThenValidationException() {
        assertThrows(ValidationException.class,
            () -> horseService.deleteHorse(1L));
    }

    @Test
    @DisplayName("Updating a horse by existing ID should not throw any Exception")
    public void givenHorseInTheDatabase_whenUpdate_ThenNameChanged() throws ValidationException, NotFoundException, ServiceException {
        Horse horse = TestData.getNewMaleHorseWithRequiredFields();
        horseService.createHorse(horse);
        horse.setName("Haku");
        horseService.updateHorse(horse);
        assertEquals(horse.getName(), "Haku");
    }
}
