package org.playerapi.player.unit;

import org.junit.jupiter.api.Test;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PlayerRowMapperTest {

    @Test
    void mapRow_correctly_maps_resultSet_to_player() throws SQLException {
        // Arrange: Mock ResultSet
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("first_name")).thenReturn("John");
        when(resultSet.getString("last_name")).thenReturn("Doe");
        when(resultSet.getInt("age")).thenReturn(25);
        when(resultSet.getString("email")).thenReturn("john.doe@example.com");

        PlayerRowMapper rowMapper = new PlayerRowMapper();

        // Act: Map the ResultSet to a Player object
        Player player = rowMapper.mapRow(resultSet, 1);

        // Assert: Verify the mapped Player object
        assertEquals(1, player.getId());
        assertEquals("John", player.getFirstName());
        assertEquals("Doe", player.getLastName());
        assertEquals(25, player.getAge());
        assertEquals("john.doe@example.com", player.getEmail());

        // Verify that the ResultSet methods were called
        verify(resultSet, times(1)).getInt("id");
        verify(resultSet, times(1)).getString("first_name");
        verify(resultSet, times(1)).getString("last_name");
        verify(resultSet, times(1)).getInt("age");
        verify(resultSet, times(1)).getString("email");
    }
}
