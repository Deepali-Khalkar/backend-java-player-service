package service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import com.app.playerservicejava.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllPlayers() {
        // Arrange
        List<Player> mockPlayers = Arrays.asList(new Player(), new Player());
        when(playerRepository.findAll()).thenReturn(mockPlayers);

        // Act
        Players players = playerService.getAllPlayers();

        // Assert
        assertNotNull(players);
        assertEquals(2, players.getPlayers().size());
        verify(playerRepository, times(1)).findAll();
    }

    @Test
    void testGetAllPlayersPagination() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> mockPage = new PageImpl<>(Arrays.asList(new Player(), new Player()));
        when(playerRepository.findAll(pageable)).thenReturn(mockPage);

        // Act
        Page<Player> result = playerService.getAllPlayersPagination(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(playerRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetAllPlayersWithPaginationAndSorting() {
        // Arrange
        int page = 0;
        int size = 10;
        String sortBy = "name";
        String direction = "asc";
        Sort sort = Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Player> mockPage = new PageImpl<>(Arrays.asList(new Player(), new Player()));
        when(playerRepository.findAll(pageable)).thenReturn(mockPage);

        // Act
        Page<Player> result = playerService.getAllPlayersWithPaginationAndSorting(page, size, sortBy, direction);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(playerRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetPlayerById() {
        // Arrange
        String playerId = "123";
        Player mockPlayer = new Player();
        when(playerRepository.findById(playerId)).thenReturn(Optional.of(mockPlayer));

        // Act
        Optional<Player> result = playerService.getPlayerById(playerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockPlayer, result.get());
        verify(playerRepository, times(1)).findById(playerId);
    }

    @Test
    void testCreatePlayer() {
        // Arrange
        Player mockPlayer = new Player();
        when(playerRepository.save(mockPlayer)).thenReturn(mockPlayer);

        // Act
        Player result = playerService.createPlayer(mockPlayer);

        // Assert
        assertNotNull(result);
        assertEquals(mockPlayer, result);
        verify(playerRepository, times(1)).save(mockPlayer);
    }

    @Test
    void testUpdatePlayer() {
        // Arrange
        String playerId = "123";
        Player existingPlayer = new Player();
        existingPlayer.setPlayerId(playerId);

        Player updatedPlayer = new Player();
        updatedPlayer.setPlayerId(playerId);
        updatedPlayer.setFirstName("Updated Name");

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(existingPlayer));
        when(playerRepository.save(existingPlayer)).thenReturn(updatedPlayer);

        // Act
        Player result = playerService.updatePlayer(updatedPlayer);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Name", result.getFirstName());
        verify(playerRepository, times(1)).findById(playerId);
        verify(playerRepository, times(1)).save(existingPlayer);
    }

    @Test
    void testDeleteById() {
        // Arrange
        String playerId = "123";
        when(playerRepository.existsById(playerId)).thenReturn(false);

        // Act
        boolean result = playerService.deleteById(playerId);

        // Assert
        assertTrue(result);
        verify(playerRepository, times(1)).deleteById(playerId);
        verify(playerRepository, times(1)).existsById(playerId);
    }

    @Test
    void testCreatePlayer_NullPlayer() {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> playerService.createPlayer(null));
        assertEquals("Player cannot be null", exception.getMessage());
        verifyNoInteractions(playerRepository);
    }
}