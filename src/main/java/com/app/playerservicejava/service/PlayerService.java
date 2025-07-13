package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository playerRepository;

    public Players getPlayers() {
        Players players = new Players();
        playerRepository.findAll()
                .forEach(players.getPlayers()::add);
        return players;
    }

    public Optional<Player> getPlayerById(String playerId) {
        Optional<Player> player = null;

        /* simulated network delay */
        try {
            player = playerRepository.findById(playerId);
            Thread.sleep((long)(Math.random() * 2000));
        } catch (Exception e) {
            LOGGER.error("message=Exception in getPlayerById; exception={}", e.toString());
            return Optional.empty();
        }
        return player;
    }

    public Players getAllPlayerByFirstName(String firstName) {
        List<Player> playerList = playerRepository.findAllPlayersByFirstName(firstName);
        Players players = new Players();
        players.setPlayers(playerList);

        return players;

    }

    public Players getAllPlayersByLastName(String lastName) {
        List<Player> playerList = playerRepository.findAllPlayersByLastName(lastName);
        Players players = new Players();
        players.setPlayers(playerList);
        return players;

    }

    public Players getAllPlayersByGivenName(String givenName) {
        List<Player> playerList = playerRepository.findAllPlayersBygivenName(givenName);
        Players players = new Players();
        players.setPlayers(playerList);
        return players;

    }

    public Players getAllPlayersByWeightBetween(String minWeight, String maxWeight) {
        List<Player> playerList = playerRepository.findAllPlayersByWeightBetween(minWeight, maxWeight);
        Players players = new Players();
        players.setPlayers(playerList);
        return players;

    }

    @Transactional
    public boolean deleteAllPlayersByFirstName(String firstName) {
        int count = playerRepository.countFirstName(firstName);
        System.out.println("count=" + count);

        if (count > 0) {
            playerRepository.deleteAllPlayersByFirstName(firstName);
        }
        return true;
    }

    public Page<Player> getPlayersPaginated(Pageable pageable) {
        Page<Player> page = playerRepository.findAll(pageable);
        return page;
    }



}
