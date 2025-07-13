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
    public ResponseEntity<Players> getAllPlayerByFirstName(@RequestParam ("firstName") String firstName) {
        Players players = playerService.getAllPlayerByFirstName(firstName);

        return new ResponseEntity<>(players, HttpStatus.OK);

    }

    @GetMapping("/findByLastName")
    public ResponseEntity<Players> getAllPlayersByLastName(@RequestParam("lastName") String lastName) {
        Players players = playerService.getAllPlayersByLastName(lastName);
        return new ResponseEntity<>(players, HttpStatus.OK);
    
    }

    @GetMapping("/findByGivenName")
    public ResponseEntity<Players> getAllPlayersByGivenName(@RequestParam("givenName") String givenName) {
        Players players = playerService.getAllPlayersByGivenName(givenName);
        return new ResponseEntity<>(players, HttpStatus.OK);

    }

    @GetMapping("/findByWeightBetween")
    public ResponseEntity<Players> getAllPlayersByWeightBetween(@RequestParam("minWeight") String minWeight,
                                                            @RequestParam("maxWeight") String maxWeight) {
        Players players = playerService.getAllPlayersByWeightBetween(minWeight, maxWeight);
        return new ResponseEntity<>(players, HttpStatus.OK);

    }

    @GetMapping("/deleteByFirstName")
    public ResponseEntity<Boolean> deleteAllPlayersByFirstName(@RequestParam("firstName") String firstName) {
        Boolean result = playerService.deleteAllPlayersByFirstName(firstName);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Player>> getPlayersPaginated(@RequestParam (name="page", defaultValue="1") int page, @RequestParam(name="size", defaultValue="5") int size,
                                                            @RequestParam (name="sortBy", defaultValue="playerId") String sortBy, @RequestParam (name="sortOrder", defaultValue="asc") String sortOrder) {
        Sort sort = sortOrder == "asc" ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Player> player = playerService.getPlayersPaginated(pageable);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }





}
