package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerIntegrationTest1 {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerService;

    private Player mockPlayer;
    private Players mockPlayers;

    @BeforeEach
    void setUp() {
        mockPlayer = new Player();
        mockPlayer.setPlayerId("1");
        mockPlayer.setFirstName("John");
        mockPlayer.setLastName("Doe");

        mockPlayers = new Players();
        mockPlayers.setPlayers(Arrays.asList(mockPlayer));
    }

    @Test
    void testGetPlayers() throws Exception {
        when(playerService.getPlayers()).thenReturn(mockPlayers);

        mockMvc.perform(get("/v1/players")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players[0].firstName").value("John"))
                .andExpect(jsonPath("$.players[0].lastName").value("Doe"));
    }

    @Test
    void testGetPlayerById() throws Exception {
        when(playerService.getPlayerById(anyString())).thenReturn(Optional.of(mockPlayer));

        mockMvc.perform(get("/v1/players/{id}", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value("1"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void testGetAllPlayerByFirstName() throws Exception {
        given(playerService.getAllPlayerByFirstName(anyString())).willReturn(mockPlayers);

        mockMvc.perform(get("/v1/players/findByFirstName?firstName=John")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players[0].firstName").value("John"));
    }

    @Test
    void testGetAllPlayersByLastName() throws Exception {
        given(playerService.getAllPlayersByLastName(anyString())).willReturn(mockPlayers);

        mockMvc.perform(get("/v1/players/findByLastName?lastName=Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players[0].lastName").value("Doe"));
    }

    @Test
    void testGetAllPlayersByGivenName() throws Exception {
        given(playerService.getAllPlayersByGivenName(anyString())).willReturn(mockPlayers);

        mockMvc.perform(get("/v1/players/findByGivenName?givenName=Johnny")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players[0].firstName").value("John"));
    }

    @Test
    void testGetAllPlayersByWeightBetween() throws Exception {
        given(playerService.getAllPlayersByWeightBetween(anyString(), anyString())).willReturn(mockPlayers);

        mockMvc.perform(get("/v1/players/findByWeightBetween?minWeight=70&maxWeight=100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.players[0].firstName").value("John"));
    }

    @Test
    void testDeleteAllPlayersByFirstName() throws Exception {
        given(playerService.deleteAllPlayersByFirstName(anyString())).willReturn(true);

        mockMvc.perform(get("/v1/players/deleteByFirstName?firstName=John")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void testGetPlayersPaginated() throws Exception {
        Page<Player> page = new PageImpl<>(Arrays.asList(mockPlayer));
        given(playerService.getPlayersPaginated(any(PageRequest.class))).willReturn(page);

        mockMvc.perform(get("/v1/players/paginated")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "firstName")
                        .param("sortOrder", "asc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].playerId").value("1"))
                .andExpect(jsonPath("$.content[0].firstName").value("John"));
    }
}