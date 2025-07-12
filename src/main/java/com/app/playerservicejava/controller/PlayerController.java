package com.app.playerservicejava.controller;

import com.app.playerservicejava.dto.PlayerPartialDTO;
import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import jakarta.websocket.server.PathParam;
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

    @GetMapping("/browse")
    public ResponseEntity<Page<Player>> getPlayersByPage
            (@RequestParam(value="page", defaultValue="0") int page,
             @RequestParam(value="size", defaultValue="5")  int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Player> pagebaleResult = playerService.getPlayersByPage(pageable);
        return new ResponseEntity<>(pagebaleResult, HttpStatus.OK);

    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<Player>> getPlayersByPageAndSorting(
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value="size", defaultValue="5") int size,
            @RequestParam(value="sortBy", defaultValue="playerId") String sortBy,
            @RequestParam(value="sortOrder", defaultValue="asc") String sortOrder
    ) {
        Sort sort = sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pagebale = PageRequest.of(page, size, sort);

        return new ResponseEntity(playerService.getPlayersByPageAndSorting(pagebale), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) throws Exception {
        return new ResponseEntity(playerService.createPlayer(player), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Player> updatePlayer(
            @RequestBody Player player
    ) {
        return new ResponseEntity( playerService.updatePlayer(player), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePlayer(@PathParam("id") String id) {
        return new ResponseEntity( playerService.deletePlayer(id), HttpStatus.OK);

    }

    @GetMapping("/findByFirstName")
    public ResponseEntity<Players> getPlayersByFirstName(@RequestParam(name="firstName") String firstName) {
        Sort sort = Sort.by("lastName").descending();
        Pageable pageable = PageRequest.of(0, 5, sort);

        return new ResponseEntity(playerService.findAllPlayersByFirstName(firstName, pageable), HttpStatus.OK);

    }

    @GetMapping("/findByFistAndLastName")
    public ResponseEntity<Player> getPlayersByFirstAndLastName(@RequestParam(name="firstName") String firstName,
                                                               @RequestParam(name="lastName") String lastName) {
        return new ResponseEntity(playerService.findAllPlayersByFirstAndLastName(firstName, lastName), HttpStatus.OK);
    }

    @GetMapping("/findByWeightBetween")
    public ResponseEntity<List<Player>> getAllPlayersWithWeightBetween(
            @RequestParam("minWeight") String minWeight,
            @RequestParam("maxWeight") String maxWeight
    ) {
        Sort sort = Sort.by("weight").descending();
        Pageable pageable = PageRequest.of(1000, 5, sort);
        return new ResponseEntity(playerService.findAllPlayersWithWeightBetween(minWeight, maxWeight, pageable), HttpStatus.OK);
    }

    @GetMapping("/partial")
    public ResponseEntity<List<PlayerPartialDTO>> getAllPlayersPartialData() {
        return new ResponseEntity(playerService.findAllPlayersPartialData(), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Player>> getAllPlayers() {
        return new ResponseEntity(playerService.findAllPlayers(), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getCount() {
        return new ResponseEntity(playerService.getCount(), HttpStatus.OK);
    }

    @GetMapping("/weightBetween")
    public ResponseEntity<List<Player>> findPlayersWithWeightBetween(
            @RequestParam("minWeight") String minWeight,
            @RequestParam("maxWeight") String maxWeight
    ) {
        List<Player> list = playerService.findAllPlayersWithWeightBetween(minWeight, maxWeight);
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity deleteByFirstName(@RequestParam("firstName") String firstName) {
        playerService.deleteByFirstName(firstName);
        return new ResponseEntity(HttpStatus.OK);

    }


}
