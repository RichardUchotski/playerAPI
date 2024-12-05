package org.playerapi.player.unit;

import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.playerapi.exceptions.*;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerDAO;
import org.playerapi.player.PlayerRequestObject;

import jakarta.persistence.EntityManager;
import org.playerapi.player.PlayerService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerDAO playerDAO;

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void getPlayers_returns_all_players() {
        List<Player> mockPlayers = List.of(
                new Player("John", "Doe", 25, "john.doe@example.com"),
                new Player("Jane", "Smith", 30, "jane.smith@example.com")
        );

        when(playerDAO.getPlayers()).thenReturn(mockPlayers);

        List<Player> result = playerService.getPlayers();

        assertEquals(2, result.size());
        verify(playerDAO, times(1)).getPlayers();
    }

    @Test
    void getPlayer_returns_player_successfully() {
        Player mockPlayer = new Player(1, "John", "Doe", 25, "john.doe@example.com");

        when(playerDAO.getPlayer(1)).thenReturn(Optional.of(mockPlayer));

        Player result = playerService.getPlayer(1);

        assertEquals(mockPlayer, result);
        verify(playerDAO, times(1)).getPlayer(1);
    }

    @Test
    void getPlayer_throws_exception_when_player_not_found() {
        when(playerDAO.getPlayer(1)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PlayerByIdNotInDatabaseException.class, () -> playerService.getPlayer(1));

        assertEquals("Player with id 1 does not exist by ID", exception.getMessage());
        verify(playerDAO, times(1)).getPlayer(1);
    }

    @Test
    void addPlayer_successfully_creates_new_player() {
        PlayerRequestObject request = new PlayerRequestObject("John", "Doe", 25, "john.doe@example.com");
        Player newPlayer = new Player("John", "Doe", 25, "john.doe@example.com");

        when(playerDAO.existsPlayerByEmail(request.email())).thenReturn(false);

        Player result = playerService.addPlayer(request);

        assertEquals(newPlayer.getEmail(), result.getEmail());
        verify(playerDAO, times(1)).addPlayer(any(Player.class));
    }

    @Test
    void addPlayer_throws_exception_when_email_already_exists() {
        PlayerRequestObject request = new PlayerRequestObject("John", "Doe", 25, "john.doe@example.com");

        when(playerDAO.existsPlayerByEmail(request.email())).thenReturn(true);

        Exception exception = assertThrows(PlayerAlreadyExistsByEmailException.class, () -> playerService.addPlayer(request));

        assertEquals("Player with email john.doe@example.com already exists", exception.getMessage());
        verify(playerDAO, never()).addPlayer(any());
    }

    @Test
    void addPlayer_throws_exception_when_name_is_invalid() {
        PlayerRequestObject request = new PlayerRequestObject("", "Doe", 25, "john.doe@example.com");

        Exception exception = assertThrows(RequestPropertyIsNotValid.class, () -> playerService.addPlayer(request));

        assertEquals("Valid first name is required", exception.getMessage());
    }

    @Test
    void updatePlayer_successfully_updates_existing_player() {
        int id = 1;
        Player existingPlayer = new Player(id, "John", "Doe", 25, "john.doe@example.com");
        PlayerRequestObject updateRequest = new PlayerRequestObject("John", "Doe", 30, "john.doe@example.com");

        when(playerDAO.getPlayer(id)).thenReturn(Optional.of(existingPlayer));

        Player updatedPlayer = playerService.updatePlayer(updateRequest, id);

        assertEquals(30, updatedPlayer.getAge());
        verify(playerDAO, times(1)).updatePlayer(existingPlayer);
    }

    @Test
    void updatePlayer_throws_exception_when_no_changes_made() {
        int id = 1;
        Player existingPlayer = new Player(id, "John", "Doe", 25, "john.doe@example.com");
        PlayerRequestObject updateRequest = new PlayerRequestObject("John", "Doe", 25, "john.doe@example.com");

        when(playerDAO.getPlayer(id)).thenReturn(Optional.of(existingPlayer));

        Exception exception = assertThrows(NoChangesMadeOnUpdateException.class, () -> playerService.updatePlayer(updateRequest, id));

        assertEquals("No Update Required as no changes made on update", exception.getMessage());
        verify(playerDAO, never()).updatePlayer(any());
    }

    @Test
    void deletePlayer_successfully_deletes_player() {
        int id = 1;
        Player existingPlayer = new Player(id, "John", "Doe", 25, "john.doe@example.com");

        when(playerDAO.getPlayer(id)).thenReturn(Optional.of(existingPlayer));

        String result = playerService.deletePlayer(id);

        assertEquals("Player with id 1 deleted", result);
        verify(playerDAO, times(1)).deletePlayer(id);
    }

    @Test
    void deletePlayer_throws_exception_when_player_not_found() {
        int id = 1;

        when(playerDAO.getPlayer(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PlayerByIdNotInDatabaseException.class, () -> playerService.deletePlayer(id));

        assertEquals("Player with id 1 does not exist by ID", exception.getMessage());
        verify(playerDAO, never()).deletePlayer(id);
    }

//    @Test
//    void deleteAllPlayers_successfully_resets_sequence_and_deletes_all() {
//        // Arrange: Mock getPlayers to return a non-empty list
//        List<Player> mockPlayers = List.of(new Player("John", "Doe", 25, "john.doe@example.com"));
//        when(playerDAO.getPlayers()).thenReturn(mockPlayers);
//
//        // Mock the behavior of EntityManager and Query
//        Query mockQuery = mock(Query.class);
//        when(entityManager.createNativeQuery("ALTER SEQUENCE player_id_seq RESTART WITH 1")).thenReturn(mockQuery);
//        when(mockQuery.executeUpdate()).thenReturn(1); // Mock successful execution
//
//        // Act: Call the service method
//        String result = playerService.deleteAllPlayers();
//
//        // Assert: Verify the results
//        assertEquals("Deleted All Players", result);
//        verify(playerDAO, times(1)).deleteAllPlayers();
//        verify(entityManager, times(1)).createNativeQuery("ALTER SEQUENCE player_id_seq RESTART WITH 1");
//        verify(mockQuery, times(1)).executeUpdate();
//    }


    @Test
    void deleteAllPlayers_throws_exception_when_no_players_in_database() {
        when(playerDAO.getPlayers()).thenReturn(List.of());

        Exception exception = assertThrows(NoPlayersInDatabaseException.class, () -> playerService.deleteAllPlayers());

        assertInstanceOf(NoPlayersInDatabaseException.class, exception);
        verify(playerDAO, never()).deleteAllPlayers();
    }
}
