package com.app.playerservicejava.service;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.repository.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerService.class);

    @Autowired
    private PlayerRepository playerRepository;

    public Players getAllPlayers() {
        LOGGER.info("Get Players from API");
        Players players = new Players();

        List<Player> playersList = playerRepository.findAll();
        players.setPlayers(playersList);

        return players;
    }

    public Page<Player> getAllPlayersPagination(Pageable pageable) {
        LOGGER.info("Get Players with Pagination from API");

        // Use the repository's paging feature
        return playerRepository.findAll(pageable);
    }

    public Page<Player> getAllPlayersWithPaginationAndSorting(int page, int size, String sortBy, String direction) {
        LOGGER.info("Get Players with Pagination and Sorting from API");
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return playerRepository.findAll(pageable);
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


    public Player createPlayer(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }

        Player newPlayer =  playerRepository.save(player);
        return newPlayer;
    }

    public Player updatePlayer(Player updatedPlayer) {

        Player existingPlayer = playerRepository.findById(updatedPlayer.getPlayerId()).orElseThrow();
        // Update fields
        existingPlayer.setBirthYear(updatedPlayer.getBirthYear());
        existingPlayer.setBirthMonth(updatedPlayer.getBirthMonth());
        existingPlayer.setBirthDay(updatedPlayer.getBirthDay());
        existingPlayer.setBirthCountry(updatedPlayer.getBirthCountry());
        existingPlayer.setBirthState(updatedPlayer.getBirthState());
        existingPlayer.setBirthCity(updatedPlayer.getBirthCity());
        existingPlayer.setDeathYear(updatedPlayer.getDeathYear());
        existingPlayer.setDeathMonth(updatedPlayer.getDeathMonth());
        existingPlayer.setDeathDay(updatedPlayer.getDeathDay());
        existingPlayer.setDeathCountry(updatedPlayer.getDeathCountry());
        existingPlayer.setDeathState(updatedPlayer.getDeathState());
        existingPlayer.setDeathCity(updatedPlayer.getDeathCity());
        existingPlayer.setFirstName(updatedPlayer.getFirstName());
        existingPlayer.setLastName(updatedPlayer.getLastName());
        existingPlayer.setGivenName(updatedPlayer.getGivenName());
        existingPlayer.setWeight(updatedPlayer.getWeight());
        existingPlayer.setHeight(updatedPlayer.getHeight());
        existingPlayer.setBats(updatedPlayer.getBats());
        existingPlayer.setThrowStats(updatedPlayer.getThrowStats());
        existingPlayer.setDebut(updatedPlayer.getDebut());
        existingPlayer.setFinalGame(updatedPlayer.getFinalGame());
        existingPlayer.setRetroId(updatedPlayer.getRetroId());
        existingPlayer.setBbrefId(updatedPlayer.getBbrefId());
        return playerRepository.save(existingPlayer);
    }

    public boolean deleteById(String id) {
        playerRepository.deleteById(id);
        if (playerRepository.existsById(id) == false) {
            return true;
        }
        return false;
    }
}
