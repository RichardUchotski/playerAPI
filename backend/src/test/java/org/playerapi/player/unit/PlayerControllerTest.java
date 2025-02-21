package org.playerapi.player.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.playerapi.exceptions.NoPlayersInDatabaseException;
import org.playerapi.exceptions.PlayerByIdNotInDatabaseException;
import org.playerapi.jwt.JWTUtil;
import org.playerapi.player.*;
import org.playerapi.utility.CreatePlayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    PlayerService playerService;
    @Mock
    PlayerDTOMapper playerDTOMapper;
    @Mock
    JWTUtil jwtUtil;

    @InjectMocks
    private PlayerController playerController;

    @Test
    void getPlayers_returns_all_players_successfully() {
        List<Player> mockPlayers = IntStream.range(0, 100)
                .mapToObj(i -> CreatePlayer.makePlayer())
                .toList();

        when(playerService.getPlayers()).thenReturn(mockPlayers);

        List<PlayerDTO> result = playerController.getPlayers();

        assertEquals(mockPlayers.size(), result.size());
        verify(playerService, Mockito.times(1)).getPlayers();
    }

    @Test
    void getPlayer_returns_player_successfully() {

        Player mockPlayer = CreatePlayer.makePlayer();

        when(playerService.getPlayer(1)).thenReturn(mockPlayer);

        PlayerDTO result = playerController.getPlayer(1);

        assertEquals(playerDTOMapper.apply(mockPlayer), result);
        verify(playerService, Mockito.times(1)).getPlayer(1);
    }

    @Test
    void getPlayer_returns_player_throws_exception() {
        int playerId = 1;

        when(playerService.getPlayer(playerId)).thenThrow(new PlayerByIdNotInDatabaseException("Player with id " + playerId + " does not exist by ID"));

        Exception exception = assertThrows(PlayerByIdNotInDatabaseException.class, () -> playerController.getPlayer(playerId));

        assertEquals("Player with id " + playerId + " does not exist by ID", exception.getMessage());

        verify(playerService, Mockito.times(1)).getPlayer(playerId);
    }

    @Test
    void addPlayer_successfully_added() {
        PlayerRequestObject request = CreatePlayer.makePlayerRequestObject();
        Player player = CreatePlayer.makePlayerFromRequest(request);

        when(playerService.addPlayer(request)).thenReturn(player);
        when(jwtUtil.issueToken(request.email(), "ROLE_USER")).thenReturn("TOKEN_JWT");
        when(playerDTOMapper.apply(player)).thenReturn(
                new PlayerDTO(
                        1, // Assuming a test ID
                        player.getFirstName(),
                        player.getLastName(),
                        player.getAge(),
                        player.getDateOfBirth(),
                        player.getPhoneNumber(),
                        player.getEmail(),
                        player.getGender(),
                        player.getTeam(),
                        player.isTermsAccepted(),
                        List.of("ROLE_USER"), // Example roles
                        player.getEmail() // Using email as username
                )
        );


        ResponseEntity<?> responseEntity = playerController.addPlayer(request);

        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity).isInstanceOf(ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);







    }

    @Test
    void updatePlayer() {
        int id = 1;
        String firstName = "First";
        String lastName = "Last";
        int age = 28;
        String dob = "2010-01-01";
        String phone = "07982305251";
        String email = "e@e.com";
        Gender gender = Gender.valueOf("MALE");
        String team = "Glasgow Korfball Club";
        boolean termsAccepted = true;



        PlayerRequestObject mockPlayerRequestObject = new PlayerRequestObject(firstName, lastName, age, dob, phone, email, "password", gender.toString(),team,termsAccepted);
        Player mockPlayer = new Player(id,firstName, lastName, age, LocalDate.parse(dob), phone, email, "password", gender,team,true);
        PlayerDTO mockDTO = playerDTOMapper.apply(mockPlayer);

        when(playerService.updatePlayer(mockPlayerRequestObject, id)).thenReturn(mockPlayer);

        assertEquals(playerDTOMapper.apply(mockPlayer), playerController.updatePlayer(mockPlayerRequestObject, id));

        verify(playerService, Mockito.times(1)).updatePlayer(mockPlayerRequestObject, id);
    }

    @Test
    void deletePlayer() {
        int playerId = 1;
        String successMessage = "Player with id " + playerId + " deleted";

        when(playerService.deletePlayer(playerId)).thenReturn(successMessage);

        String result = playerController.deletePlayer(playerId);

        assertEquals(successMessage, result);
        verify(playerService, Mockito.times(1)).deletePlayer(playerId);
    }

    @Test
    void deletePlayer_throws_exception_when_player_not_found() {
        int playerId = 1;

        when(playerService.deletePlayer(playerId)).thenThrow(new PlayerByIdNotInDatabaseException("Player with id " + playerId + " does not exist by ID"));

        Exception exception = assertThrows(PlayerByIdNotInDatabaseException.class, () -> playerController.deletePlayer(playerId));

        assertEquals("Player with id " + playerId + " does not exist by ID", exception.getMessage());
        verify(playerService, Mockito.times(1)).deletePlayer(playerId);
    }

    @Test
    void deleteAllPlayers() {
        String successMessage = "Deleted All Players";

        when(playerService.deleteAllPlayers()).thenReturn(successMessage);

        String result = playerController.deleteAllPlayers();

        assertEquals(successMessage, result);
        verify(playerService, Mockito.times(1)).deleteAllPlayers();
    }

    @Test
    void deleteAllPlayers_throws_exception_when_no_players_in_database() {
        when(playerService.deleteAllPlayers()).thenThrow(new NoPlayersInDatabaseException());

        Exception exception = assertThrows(NoPlayersInDatabaseException.class, () -> playerController.deleteAllPlayers());

        verify(playerService, Mockito.times(1)).deleteAllPlayers();
    }
}