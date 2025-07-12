package com.app.playerservicejava.service;

import com.app.playerservicejava.dto.PlayerPartialDTO;
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

import java.util.ArrayList;
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

    public Page<Player> getPlayersByPage(Pageable pageable) {
        return playerRepository.findAll(pageable);

    }

    public Page<Player> getPlayersByPageAndSorting(Pageable pageable) {
        return playerRepository.findAll(pageable);

    }

    public Player createPlayer(Player player) throws Exception {
        if (player == null) {
            throw new Exception("Player cannot be null");
        }
        return playerRepository.save(player);
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

    public boolean deletePlayer(String id) {
        Optional<Player> player = playerRepository.findById(id);
        if (player.isPresent()) {
            playerRepository.deleteById(id);
            return true;

        } else {
            return false;
        }

    }

    public Players findAllPlayersByFirstName(String firstName, Pageable pageable) {
        List<Player> playerList = playerRepository.findAllPlayersByFirstName(firstName, pageable);
        Players players = new Players();
        players.getPlayers().addAll(playerList);
        return players;
    }

    public Players findAllPlayersByFirstAndLastName(String firstName, String lastName) {
        List<Player> playerList = playerRepository.findAllPlayersByFirstNameAndLastName(firstName, lastName);
        Players players = new Players();
        players.getPlayers().addAll(playerList);
        return players;
    }

    public Players findAllPlayersWithWeightBetween(String minWeight, String maxWeight, Pageable pageable) {
        List<Player> playerList = playerRepository.findAllPlayersByWeightBetween(minWeight, maxWeight, pageable);
        Players players = new Players();
        players.getPlayers().addAll(playerList);
        return players;
    }

    public List<PlayerPartialDTO> findAllPlayersPartialData() {
        List<Object[]> playerObject = playerRepository.findAllPlayersPartialData();
        List<PlayerPartialDTO> list = new ArrayList<>();
        Players players = new Players();
        for (Object[] obj : playerObject) {
            PlayerPartialDTO player = new PlayerPartialDTO();
            player.setFirstName((String) obj[0]);
            player.setLastName((String) obj[1]);
            list.add(player);
        }
        return list;
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    public long getCount() {
        return  playerRepository.count();
    }

    public List<Player> findAllPlayersWithWeightBetween(String minWeight, String maxWeight) {
        return playerRepository.findPlayersWithinWeightRange(Integer.parseInt(minWeight), Integer.parseInt(maxWeight));
    }

    @Transactional
    public void deleteByFirstName(String firstName) {
        playerRepository.deleteByFirstName(firstName);
    }

}
