package com.app.playerservicejava.controller;

import com.app.playerservicejava.model.Player;
import com.app.playerservicejava.model.Players;
import com.app.playerservicejava.service.PlayerService;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(value = "v1/players", produces = { MediaType.APPLICATION_JSON_VALUE })
public class PlayerController {
    @Resource
    private PlayerService playerService;


    @RequestMapping(method = RequestMethod.GET)
    public  ResponseEntity<Players> getAllPlayers() {
        Players players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }

    @RequestMapping(method = RequestMethod.GET  , path = "/browse")
    public ResponseEntity<Page<Player>> getAllPlayersPagination(
            @RequestParam(value = "page", defaultValue = "0") int page,  // Default page is 0
            @RequestParam(value = "size", defaultValue = "10") int size  // Default size is 10
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Player> playersPage = playerService.getAllPlayersPagination(pageable);
        return ResponseEntity.ok(playersPage);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/paginated")
    public ResponseEntity<Page<Player>> getAllPlayersWithPaginationAndSorting(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue="playerId") String sortBy,
            @RequestParam(value = "direction", defaultValue="asc") String direction
    ) {
        Page<Player> playersPage = playerService.getAllPlayersWithPaginationAndSorting(page, size, sortBy, direction);
        return ResponseEntity.ok(playersPage);

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

    @RequestMapping(method=RequestMethod.POST)
    public Player createPlayer(@RequestBody Player player) {
        return playerService.createPlayer(player);
    }

    @RequestMapping(method=RequestMethod.PUT)
    public Player updatePlayer(@RequestBody Player player) {
        return playerService.updatePlayer(player);
    }
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public boolean deletePlayerById(@PathVariable("id") String id) {
        return playerService.deleteById(id);
    }


    /*
    @RequestMapping({"/players/{page}/{size}", "/players"})
    public ResponseEntity<Page<Player>> getAllPlayersWithOptionalPagination(
            @PathVariable(required = false) Optional<Integer> page,
            @PathVariable(required = false) Optional<Integer> size
    ) {
        int pageNumber = page.orElse(0); // Default to 0
        int pageSize = size.orElse(10); // Default to 10
        return ResponseEntity.ok(playerService.getAllPlayersWithPaginationAndSorting(pageNumber, pageSize));
    }

     */
}
