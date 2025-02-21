package org.playerapi.player.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerJPARepository;
import org.playerapi.player.PlayerJPADAO;
import org.playerapi.utility.CreatePlayer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerJPADAOTest {

    @Mock
    private PlayerJPARepository playerJPARepository;

    @InjectMocks
    private PlayerJPADAO playerJPADAO;

    @Test
    void getPlayers_returns_all_players() {
        // Arrange
        List<Player> mockPlayers = List.of(
                CreatePlayer.makePlayer(),
                CreatePlayer.makePlayer()
        );
        when(playerJPARepository.findAll()).thenReturn(mockPlayers);

        // Act
        List<Player> result = playerJPADAO.getPlayers();

        // Assert
        assertEquals(mockPlayers.size(), result.size());
        assertEquals(mockPlayers, result);
        verify(playerJPARepository, times(1)).findAll();
    }

    @Test
    void getPlayer_returns_player_when_found() {
        // Arrange
        Player mockPlayer = CreatePlayer.makePlayer();
        when(playerJPARepository.findById(1)).thenReturn(Optional.of(mockPlayer));

        // Act
        Optional<Player> result = playerJPADAO.getPlayer(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(mockPlayer, result.get());
        verify(playerJPARepository, times(1)).findById(1);
    }

    @Test
    void getPlayer_returns_empty_when_not_found() {
        // Arrange
        when(playerJPARepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Player> result = playerJPADAO.getPlayer(1);

        // Assert
        assertTrue(result.isEmpty());
        verify(playerJPARepository, times(1)).findById(1);
    }

    @Test
    void addPlayer_saves_and_returns_player() {
        // Arrange
        Player mockPlayer = CreatePlayer.makePlayer();

        // Act
        Player result = playerJPADAO.addPlayer(mockPlayer);

        // Assert
        assertEquals(mockPlayer, result);
        verify(playerJPARepository, times(1)).save(mockPlayer);
    }

    @Test
    void updatePlayer_saves_updated_player() {
        // Arrange
        Player mockPlayer = CreatePlayer.makePlayer();

        // Act
        playerJPADAO.updatePlayer(mockPlayer);

        // Assert
        verify(playerJPARepository, times(1)).save(mockPlayer);
    }

    @Test
    void deletePlayer_deletes_existing_player() {
        // Arrange
        Player mockPlayer = CreatePlayer.makePlayer();
        when(playerJPARepository.findById(1)).thenReturn(Optional.of(mockPlayer));

        // Act
        playerJPADAO.deletePlayer(1);

        // Assert
        verify(playerJPARepository, times(1)).delete(mockPlayer);
    }

    @Test
    void deletePlayer_does_nothing_when_player_not_found() {
        // Arrange
        when(playerJPARepository.findById(1)).thenReturn(Optional.empty());

        // Act
        playerJPADAO.deletePlayer(1);

        // Assert
        verify(playerJPARepository, never()).delete(any());
    }

    @Test
    void deleteAllPlayers_deletes_all_players() {
        // Act
        playerJPADAO.deleteAllPlayers();

        // Assert
        verify(playerJPARepository, times(1)).deleteAll();
    }

    @Test
    void existsPlayerByEmail_returns_true_when_email_exists() {
        // Arrange
        String email = "john.doe@example.com";
        when(playerJPARepository.existsByEmail(email)).thenReturn(true);

        // Act
        boolean result = playerJPADAO.existsPlayerByEmail(email);

        // Assert
        assertTrue(result);
        verify(playerJPARepository, times(1)).existsByEmail(email);
    }

    @Test
    void existsPlayerByEmail_returns_false_when_email_does_not_exist() {
        // Arrange
        String email = "john.doe@example.com";
        when(playerJPARepository.existsByEmail(email)).thenReturn(false);

        // Act
        boolean result = playerJPADAO.existsPlayerByEmail(email);

        // Assert
        assertFalse(result);
        verify(playerJPARepository, times(1)).existsByEmail(email);
    }
}
