package org.playerapi.player.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.playerapi.exceptions.NoPlayersInDatabaseException;
import org.playerapi.exceptions.PlayerByIdNotInDatabaseException;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerController;
import org.playerapi.player.PlayerRequestObject;
import org.playerapi.player.PlayerService;
import org.playerapi.utility.CreatePlayer;

import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    PlayerService playerService;

    @InjectMocks
    private PlayerController playerController;

    @Test
    void getPlayers_returns_all_players_successfully() {
        List<Player> mockPlayers = IntStream.range(0, 100)
                .mapToObj(i -> CreatePlayer.make())
                .toList();

        when(playerService.getPlayers()).thenReturn(mockPlayers);

        List<Player> result = playerController.getPlayers();

        assertEquals(mockPlayers.size(), result.size());
        verify(playerService, Mockito.times(1)).getPlayers();
    }

    @Test
    void getPlayer_returns_player_successfully() {
        Player mockPlayer = CreatePlayer.make();

        when(playerService.getPlayer(1)).thenReturn(mockPlayer);

        Player result = playerController.getPlayer(1);

        assertEquals(mockPlayer, result);
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

        String firstName = "First";
        String lastName = "Last";
        int age = 28;
        String email = "e@e.com";

        PlayerRequestObject mockPlayerRequestObject = new PlayerRequestObject(firstName, lastName, age, email);
        Player mockPlayer = new Player(firstName, lastName, age, email);

        when(playerService.addPlayer(mockPlayerRequestObject)).thenReturn(mockPlayer);

        assertEquals(mockPlayer, playerController.addPlayer(mockPlayerRequestObject));
        verify(playerService, Mockito.times(1)).addPlayer(mockPlayerRequestObject);
    }

    @Test
    void updatePlayer() {
        int id = 1;
        String firstName = "First";
        String lastName = "Last";
        int age = 28;
        String email = "e@e.com";

        PlayerRequestObject mockPlayerRequestObject = new PlayerRequestObject(firstName, lastName, age, email);
        Player mockPlayer = new Player(id, firstName, lastName, age, email);

        when(playerService.updatePlayer(mockPlayerRequestObject, id)).thenReturn(mockPlayer);

        assertEquals(mockPlayer, playerController.updatePlayer(mockPlayerRequestObject, id));

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