package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void getAllPlayersPageableTest() {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setFirstName("firstname1");
        player2.setFirstName("firstname2");

        List<Player> playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);

        Page<Player> page = new PageImpl<>(playerList, PageRequest.of(0, 2), playerList.size());
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());

        when(playerRepository.findAll(pageable)).thenReturn(page);
        Page<Player> player = playerService.getAllPlayersPageable(0, 2, "asc", "id");
        List<Player> actualList = player.getContent();
        assertEquals(actualList.get(0).getFirstName(), "firstname1");
        assertEquals(actualList.get(1).getFirstName(), "firstname2");






    }




}
