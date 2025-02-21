package org.playerapi.player;


import org.playerapi.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerDTOMapper playerDTOMapper;
    private final JWTUtil jwtUtil;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerDTOMapper playerDTOMapper, JWTUtil jwtUtil) {
        this.playerService = playerService;
        this.playerDTOMapper = playerDTOMapper;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<PlayerDTO> getPlayers() {
        return playerService.getPlayers().stream()
                .map(playerDTOMapper).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlayerDTO getPlayer(@PathVariable int id) {
        return  playerDTOMapper.apply(playerService.getPlayer(id));
    }

    @PostMapping
    public ResponseEntity<?> addPlayer(@RequestBody PlayerRequestObject requestObject) {
            // Dependent on the player service, which returns a player
            Player returnedPlayer = playerService.addPlayer(requestObject);
            // dependent on this that returns a token
            String token = jwtUtil.issueToken(requestObject.email(), "ROLE_USER");
            // we need to call this, and get this item, is it also dependnet on the response etoty
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token).body(playerDTOMapper.apply(returnedPlayer));

    }

    @PostMapping(value = "/params", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> addPlayer(@RequestParam Map<String, String> params){
        System.out.println(params);
        return ResponseEntity.ok("Player successfully created");
    }

    @PutMapping("{id}")
    public PlayerDTO updatePlayer(@RequestBody PlayerRequestObject requestObject, @PathVariable int id) {
        return  playerDTOMapper.apply(playerService.updatePlayer(requestObject, id));
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
