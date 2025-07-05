package controller;

import com.app.playerservicejava.controller.PlayerController;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPlayers() {
        // Arrange
        Players mockPlayers = new Players();
        mockPlayers.setPlayers(Arrays.asList(new Player(), new Player()));
        when(playerService.getAllPlayers()).thenReturn(mockPlayers);

        // Act
        ResponseEntity<Players> response = playerController.getAllPlayers();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getPlayers().size());
        verify(playerService, times(1)).getAllPlayers();
    }

    @Test
    void testGetAllPlayersPagination() {
        // Arrange
        int page = 0;
        int size = 10;
        Page<Player> mockPage = new PageImpl<>(Arrays.asList(new Player(), new Player()));
        when(playerService.getAllPlayersPagination(any())).thenReturn(mockPage);


        // Act
        ResponseEntity<Page<Player>> response = playerController.getAllPlayersPagination(page, size);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
        verify(playerService, times(1)).getAllPlayersPagination(PageRequest.of(page, size));
    }

    @Test
    void testGetAllPlayersWithPaginationAndSorting() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "playerId";
        String direction = "asc";
        Page<Player> mockPage = new PageImpl<>(Arrays.asList(new Player(), new Player()));
        when(playerService.getAllPlayersWithPaginationAndSorting(page, size, sortBy, direction)).thenReturn(mockPage);

        // Act
        ResponseEntity<Page<Player>> response = playerController.getAllPlayersWithPaginationAndSorting(page, size, sortBy, direction);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getContent().size());
        verify(playerService, times(1)).getAllPlayersWithPaginationAndSorting(page, size, sortBy, direction);
    }

    @Test
    void testGetPlayerById_Found() {
        // Arrange
        String playerId = "123";
        Player mockPlayer = new Player();
        when(playerService.getPlayerById(playerId)).thenReturn(Optional.of(mockPlayer));

        // Act
        ResponseEntity<Player> response = playerController.getPlayerById(playerId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockPlayer, response.getBody());
        verify(playerService, times(1)).getPlayerById(playerId);
    }

    @Test
    void testGetPlayerById_NotFound() {
        // Arrange
        String playerId = "123";
        when(playerService.getPlayerById(playerId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Player> response = playerController.getPlayerById(playerId);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(playerService, times(1)).getPlayerById(playerId);
    }

    @Test
    void testCreatePlayer() {
        // Arrange
        Player mockPlayer = new Player();
        when(playerService.createPlayer(mockPlayer)).thenReturn(mockPlayer);

        // Act
        Player response = playerController.createPlayer(mockPlayer);

        // Assert
        assertNotNull(response);
        assertEquals(mockPlayer, response);
        verify(playerService, times(1)).createPlayer(mockPlayer);
    }

    @Test
    void testUpdatePlayer() {
        // Arrange
        Player mockPlayer = new Player();
        when(playerService.updatePlayer(mockPlayer)).thenReturn(mockPlayer);

        // Act
        Player response = playerController.updatePlayer(mockPlayer);

        // Assert
        assertNotNull(response);
        assertEquals(mockPlayer, response);
        verify(playerService, times(1)).updatePlayer(mockPlayer);
    }

    @Test
    void testDeletePlayerById_Success() {
        // Arrange
        String playerId = "123";
        when(playerService.deleteById(playerId)).thenReturn(true);

        // Act
        boolean response = playerController.deletePlayerById(playerId);

        // Assert
        assertEquals(true, response);
        verify(playerService, times(1)).deleteById(playerId);
    }

    @Test
    void testDeletePlayerById_Failure() {
        // Arrange
        String playerId = "123";
        when(playerService.deleteById(playerId)).thenReturn(false);

        // Act
        boolean response = playerController.deletePlayerById(playerId);

        // Assert
        assertEquals(false, response);
        verify(playerService, times(1)).deleteById(playerId);
    }
}