package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/players", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlayerController {
    @Resource
    private PlayerService playerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Players> getPlayers() {
        Players players = playerService.getPlayers();
        return ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") String id) {
        Optional<Player> player = playerService.getPlayerById(id);

        if (player.isPresent()) {
            return new ResponseEntity<>(player.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findByFirstName")
    public ResponseEntity<List<Player>> getPlayersByFirstName(@RequestParam("firstName") String firstName) {
        List<Player> playerList = playerService.getPlayersByFirstName(firstName);
        return new ResponseEntity<>(playerList, HttpStatus.OK);

    }

    @GetMapping("/findByFirstAndLastName")
    public ResponseEntity<List<Player>> getPlayersByFirstAndLastName(@RequestParam("firstName") String firstName,
                                                                     @RequestParam("lastName") String lastName) {
        List<Player> playerList = playerService.getPlayersByFirstAndLastName(firstName, lastName);
        ResponseEntity<List<Player>> responseEntity = new ResponseEntity<>(playerList, HttpStatus.OK);
        return responseEntity;

    }

    @GetMapping("/findByFirstOrLastName")
    public ResponseEntity<List<Player>> getPlayersByFirstOrLastName(@RequestParam("firstName") String firstName,
                                                                    @RequestParam("lastName") String lastName) {

        List<Player> playerList = playerService.getPlayersByFirstOrLastName(firstName, lastName);
        ResponseEntity<List<Player>> responseEntity = new ResponseEntity<>(playerList, HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Player>> findAllPlayersPaginated(@RequestParam(name="page", defaultValue="1") int page, @RequestParam(name="size", defaultValue="5") int size,
                                                                @RequestParam(name="sortBy", defaultValue="id") String sortBy, @RequestParam(name="sortOrder", defaultValue="asc") String sortOrder) {

        Page<Player> player = playerService.getAllPlayersPageable(page, size, sortOrder, sortBy);
        ResponseEntity<Page<Player>> entity = new ResponseEntity<>(player, HttpStatus.OK);
        return entity;

    }

    @PostMapping("/create")
    public ResponseEntity<Player> savePlayer(@RequestBody Player player) {
//        if (player.getPlayerId() == null || player.getPlayerId().isEmpty()) {
//            player.setPlayerId(UUID.randomUUID().toString());
//        }
        Player result = playerService.savePlayer(player);
        ResponseEntity<Player> entity = new ResponseEntity<>(result, HttpStatus.OK);
        return entity;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable("id") String id, @RequestBody Player player) {
        Player result = playerService.savePlayer(player);
        ResponseEntity<Player> entity = new ResponseEntity<>(result, HttpStatus.OK);
        return entity;
    }

    @PatchMapping("/partialupdate/{id}")
    public ResponseEntity<Player> updatePartialPlayer(@PathVariable("id") String id, @RequestBody Player player) throws Exception {
        Player result = playerService.updatePartialPlayer(id, player);
        ResponseEntity<Player> entity = new ResponseEntity<>(result, HttpStatus.OK);
        return entity;
    }

    @PatchMapping("/updateFirstAndLastName/{id}")
    public ResponseEntity<Player> updateFirstAndLastName(@PathVariable("id") String id, @RequestParam ("firstName") String firstName,
                                                         @RequestParam ("lastName") String lastName) throws Exception {

        Player result = playerService.updateFirstAndLastName(id, firstName, lastName);
        ResponseEntity<Player> entity = new ResponseEntity<>(result, HttpStatus.OK);
        return entity;

    }
}
