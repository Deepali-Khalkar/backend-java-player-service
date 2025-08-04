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

    public List<Player>  getPlayersByFirstName(String firstName) {

        List<Player> playerList = playerRepository.findByFirstName(firstName);

        return playerList;

    }

    public  List<Player> getPlayersByFirstAndLastName(String firstName, String lastName) {
        List<Player> playerList = playerRepository.findByFirstNameAndLastName(firstName, lastName);
        return playerList;
    }

    public List<Player> getPlayersByFirstOrLastName(String firstName, String lastName) {
        List<Player> playerList = playerRepository.findByFirstNameOrLastName(firstName, lastName);
        return playerList;
    }

    public Page<Player> getAllPlayersPageable(int page, int size, String sortOrder, String sortBy) {
        Sort sort = sortOrder == "asc" ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Player> player = playerRepository.findAll(pageable);
        return player;
    }

    public Player savePlayer(Player player) {
        Player result = playerRepository.save(player);
        return result;
    }

    public Player updatePlayer(String id, Player newPlayer) {
        Player result = playerRepository.save(newPlayer);
        return result;
    }

    public Player updatePartialPlayer(String id, Player newPlayer) throws  Exception {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {
            Player existingPlayer = optionalPlayer.get();

            //copy newPlayer to existingPlayer and save
            if (newPlayer.getBirthYear() != null) {
                existingPlayer.setBirthYear(newPlayer.getBirthYear());
            }
            if (newPlayer.getBirthMonth() != null) {
                existingPlayer.setBirthMonth(newPlayer.getBirthMonth());
            }
            if (newPlayer.getBirthDay() != null) {
                existingPlayer.setBirthDay(newPlayer.getBirthDay());
            }
            if (newPlayer.getBirthCountry() != null) {
                existingPlayer.setBirthCountry(newPlayer.getBirthCountry());
            }
            if (newPlayer.getBirthState() != null) {
                existingPlayer.setBirthState(newPlayer.getBirthState());
            }

            Player result = playerRepository.save(existingPlayer);
            return result;


        } else {
            throw new Exception("Player id not found");
        }

    }

    @Transactional
    public Player updateFirstAndLastName(String id, String firstName, String lastName) throws Exception {
        Optional<Player> optionalPlayer = playerRepository.findById(id);
        if (optionalPlayer.isPresent()) {

            playerRepository.updateFirstAndLastName(firstName, lastName, id);
            playerRepository.flush(); // Ensure update query is committed

            return playerRepository.findById(id).get();

        } else {
            throw new Exception("Player id not found");
        }


    }

}
