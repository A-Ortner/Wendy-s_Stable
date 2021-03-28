package at.ac.tuwien.sepm.assignment.individual.integration;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test", "datagen"})
public class HorseEndpointTest {

    /**
     * URI Data
     */
    private static final String BASE_URL = "http://localhost:";
    private static final String HORSE_URL = "/horses";

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    @LocalServerPort
    private int port;

    @Test(expected = HttpClientErrorException.class)
    @DisplayName("Saving a horse where name is not set returns status 422.")
    public void givenNameEmpty_whenCreatingNewHorse_thenStatus422AndGetBadRequest() {
        HttpEntity<HorseDto> request = new HttpEntity<>(TestData.getNewHorseDtoWithNameNull());
        ResponseEntity<HorseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.POST, request, HorseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        HorseDto horseResponse = response.getBody();
        assertNotNull(horseResponse);
    }

    @Test
    @DisplayName("Saving a (correct) horse returns status 201.")
    public void whenSaveOneHorse_thenStatus201AndGetGeneratedId() {

        HorseDto horseDto = TestData.getNewMaleHorseDtoWithRequiredFields();
        HttpEntity<HorseDto> request = new HttpEntity<>(horseDto);
        ResponseEntity<HorseDto> response = REST_TEMPLATE.
            postForEntity(BASE_URL + port + HORSE_URL, new HttpEntity<>(horseDto), HorseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        HorseDto horseResponse = response.getBody();
        assertNotNull(horseResponse);
        assertNotNull(horseResponse.getId());
    }

    @Test(expected = HttpClientErrorException.class)
    @DisplayName("Update horse where id does not exist should return status 404.")
    public void updateHorse_thenIdDoesNotExist_thenStatus404NotFound() {
        HorseDto horseDto = TestData.getNewHorseDtoWithInvalidId();

        HttpEntity<HorseDto> request = new HttpEntity<>(horseDto);
        ResponseEntity<HorseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL, HttpMethod.PUT, request, HorseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        HorseDto horseResponse = response.getBody();
        assertNotNull(horseResponse);
    }

    @Test(expected = HttpClientErrorException.class)
    @DisplayName("Deleting a horse with a non-existent id should return status 422.")
    public void givenHorse_whenDeleteWithIdNotInDatabase_then422AndValidationException() {
        HorseDto horseDto = TestData.getNewHorseDtoWithInvalidId();
        HttpEntity<HorseDto> request = new HttpEntity<>(horseDto);

        ResponseEntity<HorseDto> response = REST_TEMPLATE
            .exchange(BASE_URL + port + HORSE_URL + "/" + horseDto.getId(), HttpMethod.DELETE, request, HorseDto.class);
        assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
        HorseDto horseResponse = response.getBody();
        assertNotNull(horseResponse);

    }
}
