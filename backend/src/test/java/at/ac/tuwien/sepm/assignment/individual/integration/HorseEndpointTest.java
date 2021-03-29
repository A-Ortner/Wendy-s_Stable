package at.ac.tuwien.sepm.assignment.individual.integration;

import at.ac.tuwien.sepm.assignment.individual.base.TestData;
import at.ac.tuwien.sepm.assignment.individual.endpoint.dto.HorseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = {"test", "datagen"})
public class HorseEndpointTest {

    @LocalServerPort
    int randomServerPort;

    private static final String BASE_URL = "http://localhost:";
    private static final String HORSE_URL = "/horses";

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Test
    @DisplayName("Saving a (correct) horse returns status 201.")
    public void whenSaveOneHorse_thenStatus201AndGetGeneratedId() throws URISyntaxException {
        HorseDto toBeSaved = TestData.getNewHorseDtoWithValidFields();
        URI uri = new URI(BASE_URL + randomServerPort + HORSE_URL);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<HorseDto> request = new HttpEntity<>(toBeSaved, headers);

        ResponseEntity<HorseDto> response = REST_TEMPLATE.postForEntity(uri, request, HorseDto.class);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        HorseDto horseResponse = response.getBody();
        assertNotNull(horseResponse);
        assertNotNull(horseResponse.getId());
    }


    @Test()
    @DisplayName("Saving a horse where name is not set returns status 422.")
    public void givenNameEmpty_whenCreatingNewHorse_thenStatus400AndGetBadRequest() {
        HorseDto nameIsNull_HorseDto = TestData.getNewHorseDtoWithNameNull();
        HttpEntity<HorseDto> request = new HttpEntity<>(nameIsNull_HorseDto);

        assertThrows(HttpClientErrorException.class,() -> {
            ResponseEntity<HorseDto> response = REST_TEMPLATE
                .exchange(BASE_URL + randomServerPort + HORSE_URL, HttpMethod.POST, request, HorseDto.class);

            assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
            HorseDto horseResponse = response.getBody();
            assertNotNull(horseResponse);
        });
    }

    @Test()
    @DisplayName("Update horse where id does not exist should return status 422.")
    public void updateHorse_thenIdDoesNotExist_thenUnprocessableEntity(){
        HorseDto id_invalid_HorseDto = TestData.getNewHorseDtoWithInvalidId();
        HttpEntity<HorseDto> request = new HttpEntity<>(id_invalid_HorseDto);

        assertThrows(HttpClientErrorException.class,() -> {
            ResponseEntity<HorseDto> response = REST_TEMPLATE
                .exchange(BASE_URL + randomServerPort + HORSE_URL, HttpMethod.PUT, request, HorseDto.class);

            assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
            HorseDto horseResponse = response.getBody();
            assertNotNull(horseResponse);
        });
    }

    @Test()
    @DisplayName("Deleting a horse with a non-existent id should return status 422.")
    public void givenHorse_whenDeleteWithIdNotInDatabase_then404AndValidationException(){
        HorseDto id_invalid_HorseDto = TestData.getNewHorseDtoWithInvalidId();
        HttpEntity<HorseDto> request = new HttpEntity<>(id_invalid_HorseDto);

        assertThrows(HttpClientErrorException.class,() -> {
            ResponseEntity<HorseDto> response = REST_TEMPLATE
                .exchange(BASE_URL + randomServerPort + HORSE_URL, HttpMethod.DELETE, request, HorseDto.class);

            assertEquals(response.getStatusCode(), HttpStatus.UNPROCESSABLE_ENTITY);
            HorseDto horseResponse = response.getBody();
            assertNotNull(horseResponse);
        });
    }
}
