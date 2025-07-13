package com.app.playerservicejava.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.app.playerservicejava.model.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    public void testgetPlayers() {
        Players players = new Players();
        Player player = new Player();
        player.setFirstName("FirstName");
        player.setLastName("LastName");
        players.getPlayers().add(player);

        when(playerService.getPlayers()).thenReturn(players);
        ResponseEntity<Players> responseEntity = playerController.getPlayers();
        assertEquals(players, responseEntity.getBody());
    }

    @Test
    public void testgetAllPlayerByFirstName() {
        Players players = new Players();
        Player player = new Player();
        player.setFirstName("FirstName");
        player.setLastName("LastName");
        players.getPlayers().add(player);

        when(playerService.getAllPlayerByFirstName(anyString())).thenReturn(players);
        ResponseEntity<Players> responseEntity = playerController.getAllPlayerByFirstName(anyString());
        assertEquals(players, responseEntity.getBody());
    }

}
