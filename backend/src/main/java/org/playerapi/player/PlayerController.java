package org.playerapi.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping(value = "/params", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> addPlayer(@RequestParam Map<String, String> params){
        System.out.println(params);
        return ResponseEntity.ok("Player successfully created");
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
