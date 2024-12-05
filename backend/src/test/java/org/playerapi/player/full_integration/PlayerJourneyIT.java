package org.playerapi.player.full_integration;

import org.junit.jupiter.api.Test;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerRequestObject;
import org.playerapi.utility.CreatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerJourneyIT {

    @Autowired
    private WebTestClient webClient;

    private static final String PLAYER_URI = "/api/v1/players";

    @Test
    void canRegisterPlayer(){
        Player playerToRegister = CreatePlayer.make();
        System.out.println(playerToRegister);
        PlayerRequestObject playerRequestObject = new PlayerRequestObject(playerToRegister.getFirstName(), playerToRegister.getLastName(), playerToRegister.getAge(), playerToRegister.getEmail());

        webClient.post().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(playerRequestObject), PlayerRequestObject.class).exchange().expectStatus().isOk();
    }


}
