package org.playerapi.player.full_integration;

import org.junit.jupiter.api.Test;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerDTO;
import org.playerapi.player.PlayerRequestObject;
import org.playerapi.utility.CreatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.*;

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
        Player playerToRegister = CreatePlayer.makePlayer();
        PlayerRequestObject playerRequestObject =
                new PlayerRequestObject(playerToRegister.getFirstName(), playerToRegister.getLastName(), playerToRegister.getAge(), playerToRegister.getDateOfBirth().toString(),playerToRegister.getPhoneNumber(), playerToRegister.getEmail(), "password", playerToRegister.getGender().name(), playerToRegister.getTeam(), true);

        webClient.post().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(playerRequestObject), PlayerRequestObject.class).exchange().expectStatus()
                        .isOk();
    }

    private EntityExchangeResult<PlayerDTO> registerPlayer() {
        PlayerRequestObject requestObject = CreatePlayer.makePlayerRequestObject();

       return webClient.post().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(requestObject), PlayerRequestObject.class).exchange().expectStatus()
                .isOk().expectBody(PlayerDTO.class).returnResult();



    }

    @Test
    void canGetPlayers(){

        List<String> authList = new ArrayList<>();

        for(int i=0; i<4; i++){
            EntityExchangeResult<PlayerDTO> playerDTOEntityExchangeResult = registerPlayer();
            authList.add(Objects.requireNonNull(playerDTOEntityExchangeResult.getResponseHeaders().get(HttpHeaders.AUTHORIZATION)).get(0));

        }


//        The HTTp request header, the acceptok, is a header in the request, that says I can accept json, I want to work with JSON
        List<PlayerDTO> players = webClient.get().uri(PLAYER_URI)
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authList.get(0))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(new ParameterizedTypeReference<PlayerDTO>() {})
                .returnResult()
                .getResponseBody();

        assertThat(players).isNotEmpty();

        for(Object p : players){
            assertInstanceOf(PlayerDTO.class, p, "Not an instance of player");
        }

       assertThat(players).allMatch(Objects::nonNull);
    }

    @Test
    void canGetPlayer(){
        canRegisterPlayer();
    }


    @Test
    void canDeletePlayer(){
        Player playerToRegister = CreatePlayer.makePlayer();
        PlayerRequestObject playerRequestObject =
                new PlayerRequestObject(playerToRegister.getFirstName(), playerToRegister.getLastName(), playerToRegister.getAge(), playerToRegister.getDateOfBirth().toString(),playerToRegister.getPhoneNumber(), playerToRegister.getEmail(), "password", playerToRegister.getGender().name(), playerToRegister.getTeam(), true);


        FluxExchangeResult<PlayerDTO> result =  webClient.post().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(playerRequestObject), PlayerRequestObject.class)
                .exchange()
                .expectStatus()
                .isOk().returnResult(PlayerDTO.class);

        // Getting the JWT Token
        String authToken = result.getResponseHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        // Extract the player info
        PlayerDTO createdPlayer = result.getResponseBody().blockFirst();

        assertThat(createdPlayer).isNotNull();

        webClient.delete().uri(PLAYER_URI + "/" + createdPlayer.id())
                .accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .exchange().expectStatus().isOk();

    }

    @Test
    void canUpdateCustomer(){
        // Create a player and save it to the database, get the auth and the player
        Pair<String, PlayerDTO> authAndPlayerDTOPair = createPlayerDTOAndAuthToken();
        String authToken = authAndPlayerDTOPair.getFirst();
        System.out.println("ATH"+ authToken);
        PlayerDTO createdDTO = authAndPlayerDTOPair.getSecond();
        int playerId = createdDTO.id();

        // Create a JSON object for a post changing the player
        Map<String,Object> updatedPayload = new HashMap<>();
        updatedPayload.put("firstName", "Updated Player Name");


        // changing the player
        webClient.put().uri(PLAYER_URI + "/" + playerId).accept(MediaType.APPLICATION_JSON).header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken).contentType(MediaType.APPLICATION_JSON)
                .bodyValue(updatedPayload).exchange().expectStatus().isOk();

        PlayerDTO returnedDTO = webClient.get().uri(PLAYER_URI + "/" + playerId).accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + authToken)
                .exchange().expectStatus().isOk().expectBody(PlayerDTO.class).returnResult().getResponseBody();

    }

    private Pair<String,PlayerDTO> createPlayerDTOAndAuthToken() {
        Player playerToRegister = CreatePlayer.makePlayer();
        PlayerRequestObject playerRequestObject =
                new PlayerRequestObject(playerToRegister.getFirstName(), playerToRegister.getLastName(), playerToRegister.getAge(), playerToRegister.getDateOfBirth().toString(),playerToRegister.getPhoneNumber(), playerToRegister.getEmail(), "password", playerToRegister.getGender().name(), playerToRegister.getTeam(), true);

        FluxExchangeResult<PlayerDTO>  result = webClient.post().uri(PLAYER_URI).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(playerRequestObject), PlayerRequestObject.class)
                .exchange()
                .expectStatus().isOk().returnResult(PlayerDTO.class);

        String authToken = result.getResponseHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        PlayerDTO createdPlayer = result.getResponseBody().blockFirst();

        assert authToken != null;
        assert createdPlayer != null;
        return Pair.of(authToken, createdPlayer);

    }


}
