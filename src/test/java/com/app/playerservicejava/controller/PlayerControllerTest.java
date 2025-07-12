package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testgetPlayerById() {
        Player player = new Player();
        player.setFirstName("FirstName");
        Optional<Player> optionalPlayer = Optional.of(player);
        when(playerService.getPlayerById(anyString())).thenReturn(optionalPlayer);
        ResponseEntity<Player> actualResponse =  playerController.getPlayerById(anyString());
        assertEquals(actualResponse.getBody().getFirstName(), player.getFirstName());
    }

    @Test
    public void testgetPlayersByFirstName() {
        Player player = new Player();
        player.setFirstName("FirstName");
        Players players = new Players();
        players.getPlayers().add(player);
        when(playerService.findAllPlayersByFirstName(anyString(), any(Pageable.class))).thenReturn(players);
        ResponseEntity<Players> actualResponse = playerController.getPlayersByFirstName("test");
        assertEquals(actualResponse.getBody().getPlayers().get(0).getFirstName(), player.getFirstName());

    }

}
