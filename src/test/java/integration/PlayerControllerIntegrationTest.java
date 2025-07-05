package integration;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = com.app.playerservicejava.PlayerServiceJavaApplication.class // Explicitly specify the configuration class
)
@ActiveProfiles("test") // Use a specific test profile
//@EntityScan(basePackages = "com.app.playerservicejava.model") // Scan for entities
//@EnableJpaRepositories(basePackages = "com.app.playerservicejava.repository") // Enable JPA Repositories
//@ComponentScan(basePackages = "com.app.playerservicejava")

class PlayerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerControllerIntegrationTest(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    private RestTemplate restTemplate;

    private String baseUrl;

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port + "/v1/players";
        restTemplate = new RestTemplate();

        // Clean up and prepare test data
        playerRepository.deleteAll();
        Player player = new Player();
        player.setPlayerId("1");
        player.setFirstName("John");
        player.setLastName("Doe");
        playerRepository.save(player);
    }

    @Test
    void testGetAllPlayers() {
        // Create the headers
        HttpHeaders headers = new HttpHeaders();

        // Encode admin credentials in Base64
        String adminCredentials = "user:user123"; // Replace with actual credentials
        String encodedCredentials = Base64.getEncoder().encodeToString(adminCredentials.getBytes());
        headers.add("Authorization", "Basic " + encodedCredentials); // Add Basic Auth header

        // Add headers to the HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send the GET request with headers
        ResponseEntity<Players> response = restTemplate.exchange(baseUrl, HttpMethod.GET, entity, Players.class);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().getPlayers().size(), equalTo(1));
        assertThat(response.getBody().getPlayers().get(0).getFirstName(), equalTo("John"));
        assertThat(response.getBody().getPlayers().get(0).getLastName(), equalTo("Doe"));


    }

//    @Test
//    void testGetPlayerById() {
//        // Create the headers
//        HttpHeaders headers = new HttpHeaders();
//
//        // Encode admin credentials in Base64
//        String adminCredentials = "user:user123"; // Replace with actual credentials
//        String encodedCredentials = Base64.getEncoder().encodeToString(adminCredentials.getBytes());
//        headers.add("Authorization", "Basic " + encodedCredentials); // Add Basic Auth header
//
//        // Add headers to the HttpEntity
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        String url = baseUrl + "/1";
//
//        var response = restTemplate.getForEntity(url ,entity, Player.class);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertThat(response.getBody().getFirstName(), equalTo("John"));
//    }

    //@Test
    @Ignore
    void testGetPlayerById_NotFound() {
        String url = baseUrl + "/999";

        var response = restTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //@Test
    @Ignore
    void testCreatePlayer() {
        Player newPlayer = new Player();
        newPlayer.setPlayerId("2");
        newPlayer.setFirstName("Jane");

        newPlayer.setLastName("Doe");

        var response = restTemplate.postForEntity(baseUrl, newPlayer, Player.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertThat(response.getBody().getFirstName(), equalTo("Jane"));

        Optional<Player> player = playerRepository.findById("2");
        assertThat(player.isPresent(), is(true));
    }

    //@Test
    @Ignore
    void testUpdatePlayer() {
        String url = baseUrl;

        Player updatedPlayer = new Player();
        updatedPlayer.setPlayerId("1");
        updatedPlayer.setFirstName("John");
        updatedPlayer.setLastName("Updated");

        restTemplate.put(url, updatedPlayer);

        Optional<Player> player = playerRepository.findById("1");
        assertThat(player.isPresent(), is(true));
        assertEquals("John", player.get().getFirstName());
    }

    //@Test
    @Ignore
    void testDeletePlayerById() {
        String url = baseUrl + "/1";

        restTemplate.delete(url);

        Optional<Player> player = playerRepository.findById("1");
        assertThat(player.isEmpty(), is(true));
    }
}