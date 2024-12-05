package org.playerapi.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {

    PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<Player> getPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable int id) {
        return playerService.getPlayer(id);
    }

    @PostMapping
    public Player addPlayer(@RequestBody PlayerRequestObject requestObject) {
        return playerService.addPlayer(requestObject);
    }

    @PutMapping("{id}")
    public Player updatePlayer(@RequestBody PlayerRequestObject requestObject, @PathVariable int id) {
        return  playerService.updatePlayer(requestObject, id);
    }

    @DeleteMapping("{id}")
    public String deletePlayer(@PathVariable int id) {
         return playerService.deletePlayer(id);
    }

    @DeleteMapping
    public String deleteAllPlayers() {
        return playerService.deleteAllPlayers();
    }
}
