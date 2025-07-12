package com.app.playerservicejava.service;

import com.app.playerservicejava.dto.PlayerPartialDTO;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)  // Enable Mockito annotations processing
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Before
    public void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testgetPlayers() {
        List<Player> player = new ArrayList<>();
        Player player1 = new Player();
        player1.setFirstName("First1");
        Player player2 = new Player();
        player2.setFirstName("First2");
        player.add(player1);
        player.add(player2);
        when(playerRepository.findAll()).thenReturn(player);
        Players players = playerService.getPlayers();
        assertEquals(players.getPlayers().size(), players.getPlayers().size());
        assertEquals(players.getPlayers().get(0).getFirstName(), player1.getFirstName());
        assertEquals(players.getPlayers().get(1).getFirstName(), player2.getFirstName());
    }

    @Test
    public void testgetPlayerById() {

        Player player1 = new Player();
        player1.setFirstName("First1");

        Optional<Player> player = Optional.of(player1);

        when(playerRepository.findById(anyString())).thenReturn(player);
        Optional<Player> actualResult = playerService.getPlayerById(anyString());
        assertEquals(player, actualResult);
        assertEquals(player1.getFirstName(), actualResult.get().getFirstName());
    }

    @Test
    public void testgetPlayerByIdException() {
        doThrow(new RuntimeException("test")).when(playerRepository).findById(anyString());
        Optional<Player> actualResult = playerService.getPlayerById(anyString());
        assertEquals(Optional.empty(), actualResult);

    }

    @Test
    public void testfindAllPlayersPartialData() {
        List<Object[]> playerObject = new ArrayList<>();
        Object[] obj = new Object[2];
        obj[0] = "first name";
        obj[1] = "last name";
        playerObject.add(obj);
        when(playerRepository.findAllPlayersPartialData()).thenReturn(playerObject);
        List<PlayerPartialDTO> actualResult = playerService.findAllPlayersPartialData();
        assertEquals(playerObject.size(), actualResult.size());
        assertEquals(playerObject.get(0)[0].toString(), obj[0].toString());
    }


}
