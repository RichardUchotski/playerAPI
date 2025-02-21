package org.playerapi.utility.unit;

import org.junit.jupiter.api.Test;
import org.playerapi.player.Player;
import org.playerapi.utility.CreatePlayer;

import static org.junit.jupiter.api.Assertions.*;

class CreatePlayerTest {

    @Test
    void make_creates_player_successfully() {
        // Act
        Player player = CreatePlayer.makePlayer();

        // Assert
        // Verify non-null values
        assertNotNull(player.getFirstName(), "First name should not be null");
        assertNotNull(player.getLastName(), "Last name should not be null");
        assertNotNull(player.getEmail(), "Email should not be null");

        // Verify age is within valid range
        assertTrue(player.getAge() >= 18 && player.getAge() <= 100, "Age should be between 18 and 100");

        // Verify first and last names are capitalized
        assertTrue(Character.isUpperCase(player.getFirstName().charAt(0)), "First name should start with an uppercase letter");
        assertTrue(Character.isUpperCase(player.getLastName().charAt(0)), "Last name should start with an uppercase letter");
    }
}
