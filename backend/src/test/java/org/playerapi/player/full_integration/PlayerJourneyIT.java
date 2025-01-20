package org.playerapi.player.full_integration;

import org.junit.jupiter.api.Test;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerRequestObject;
import org.playerapi.utility.CreatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.anyOf;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerJourneyIT {

    @Autowired
    private WebTestClient webClient;

    private static final String PLAYER_URI = "/api/v1/players";

    @Test
    void canRegisterPlayer(){
        Player playerToRegister = CreatePlayer.make();
        PlayerRequestObject playerRequestObject =
                new PlayerRequestObject(playerToRegister.getFirstName(), playerToRegister.getLastName(), playerToRegister.getAge(), playerToRegister.getDateOfBirth().toString(),playerToRegister.getPhoneNumber(), playerToRegister.getEmail(), playerToRegister.getGender().name(), playerToRegister.getTeam(), "on");

        webClient.post().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(playerRequestObject), PlayerRequestObject.class).exchange().expectStatus().isOk();
    }

    @Test
    void canGetPlayers(){
        for(int i=0; i<4; i++){
            canRegisterPlayer();
        }

//        The HTTp request header, the acceptok, is a header in the request, that says I can accept json, I want to work with JSON
        List<Player> players = webClient.get().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<Player>() {})
                .returnResult()
                .getResponseBody();

        assertThat(players).isNotEmpty();

        for(Object p : players){
            assertInstanceOf(Player.class, p, "Not an instance of player");
        }

       assertThat(players).allMatch(Objects::nonNull);
    }

    @Test
    void canGetPlayer(){
        canRegisterPlayer();
    }


}
