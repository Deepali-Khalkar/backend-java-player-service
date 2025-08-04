package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerControllerTest {

    @Mock
    private PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    public void testgetPlayerById() {
        Player player = new Player();
        player.setFirstName("firstname");
        Optional<Player> optionalPlayer = Optional.of(player);
        when(playerService.getPlayerById(anyString())).thenReturn(optionalPlayer);
        ResponseEntity<Player> entity = playerController.getPlayerById("id");
        assertEquals(entity.getBody().getFirstName(), "firstname");
    }

    @Test
    public void findAllPlayersPaginated() {
        Player player1 = new Player();
        player1.setPlayerId("1");

        Player player2 = new Player();
        player2.setPlayerId("2");

        // Create a list of mock players
        List<Player> mockPlayers = Arrays.asList(
                player1,
                player2
        );

        Page<Player> page = new PageImpl<>(mockPlayers, PageRequest.of(0,2), mockPlayers.size());
        when(playerService.getAllPlayersPageable(0, 2, "asc", "id")).thenReturn(page);
        ResponseEntity<Page<Player>> entity = playerController.findAllPlayersPaginated(0, 2, "id", "asc");
        Page<Player> body = entity.getBody();
        List<Player> players = body.getContent();

        System.out.println(players);
        assertEquals(entity.getStatusCode(), HttpStatus.OK);
        assertEquals(players.get(0).getPlayerId(), "1");
        assertEquals(players.get(1).getPlayerId(), "2");

    }



}
