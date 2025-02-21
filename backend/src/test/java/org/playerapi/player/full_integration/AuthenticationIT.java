package org.playerapi.player.full_integration;

import org.junit.jupiter.api.Test;
import org.playerapi.jwt.JWTUtil;
import org.playerapi.login.AuthenticationResponse;
import org.playerapi.login.LoginRequestDTO;
import org.playerapi.player.PlayerDTO;
import org.playerapi.player.PlayerRequestObject;
import org.playerapi.utility.CreatePlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationIT {

    @Autowired
    private WebTestClient webClient;
    @Autowired
    private JWTUtil jwtUtil;

    private static final String AUTH_URI = "/api/v1/auth/login";
    private static final String PLAYER_URI = "/api/v1/players";


    @Test
    void cannot_Login_ThrowsUnauthorizedException() {
        PlayerRequestObject playerRequestObject = CreatePlayer.makePlayerRequestObject();
        webClient.post().uri(AUTH_URI).bodyValue(playerRequestObject).exchange().expectStatus().isUnauthorized();
    }

    @Test
    void can_logIn_with_username_and_password() {

        // Creating a player with a password
        PlayerRequestObject playerRequestObject = CreatePlayer.makePlayerRequestObject();
        String password = playerRequestObject.password();
        String username = playerRequestObject.email();

        webClient.post().uri(PLAYER_URI).bodyValue(playerRequestObject).exchange().expectStatus().isOk();
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO(username, password);
        EntityExchangeResult<AuthenticationResponse> authenticationResponseEntityExchangeResult = webClient.post().uri(AUTH_URI).bodyValue(loginRequestDTO).exchange().expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
                }).returnResult();

        String jwtToken = authenticationResponseEntityExchangeResult.getResponseHeaders().getFirst("Authorization");
        PlayerDTO playerDTO = Objects.requireNonNull(authenticationResponseEntityExchangeResult.getResponseBody()).playerDTO();


        assertThat(jwtUtil.isTokenValid(jwtToken,playerDTO.username())).isTrue();
        assertThat(playerDTO.username()).isEqualTo(username);




    }
}
