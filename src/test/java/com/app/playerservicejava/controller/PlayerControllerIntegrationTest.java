package com.app.playerservicejava.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.app.playerservicejava.controller.PlayerController;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;

@WebMvcTest(PlayerController.class)
public class PlayerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    private Players mockPlayers;
    private Player mockPlayer;

     @BeforeEach
     public void setUp() {
        mockPlayers = new Players();
        mockPlayer = new Player();
        mockPlayer.setPlayerId("1");
        mockPlayer.setFirstName("Jon");
        mockPlayer.setLastName("Doe");
        mockPlayers.getPlayers().add(mockPlayer);
     }

     @Test
     public void testgetPlayersPaginated() throws Exception {

        Page<Player> page = new PageImpl<>(Arrays.asList(mockPlayer));

        when(playerService.getPlayersPaginated(any())).thenReturn(page);

        mockMvc.perform(get("/v1/players/paginated")
        .param("page", "1")
        .param("size", "5")
        .param("sortBy", "playerId")
        .param("sortOrder", "asc")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].firstName").value("Jon"))
                .andExpect(jsonPath("$.content[0].lastName").value("Doe"));


     }







}