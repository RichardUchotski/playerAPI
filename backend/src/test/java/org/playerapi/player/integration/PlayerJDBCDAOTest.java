package org.playerapi.player.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.playerapi.AbstractTestcontainers;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerJDBCDAO;
import org.playerapi.player.PlayerRowMapper;
import org.playerapi.utility.CreatePlayer;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;


class PlayerJDBCDAOTest extends AbstractTestcontainers {

    private PlayerJDBCDAO underTest;
    private final PlayerRowMapper playerRowMapper = new PlayerRowMapper();

    @BeforeEach
    void setUp() {
        underTest = new PlayerJDBCDAO(getJdbcTemplate(),playerRowMapper);
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAllPlayers();
    }

    @Test
    void getPlayers() {
        // The JDBC template is what performs our crud operations, so
        // what we are doing is adding the players to the database, and then
        //
        for(int i=0; i<10; i++){
            underTest.addPlayer(CreatePlayer.makePlayer());
        }

        List<Player> actual = underTest.getPlayers();

        assertThat(actual.size()).isEqualTo(10);
        assertThat(actual.get(0)).isInstanceOf(Player.class).extracting("firstName").asString().matches("[A-Z]+[a-z]*");
        assertThat(actual.get(0)).isInstanceOf(Player.class).extracting("lastName").asString().matches("[A-Z]+[a-z]*");
        assertThat(actual.get(0).getAge()).isGreaterThan(18).isLessThan(101).isNotNull();
        assertThat(actual.get(1)).isInstanceOf(Player.class);
        Assertions.assertInstanceOf(Player.class, actual.get(2));
    }

    @Test
    void getPlayer_isSuccessful() {
        Player mockPlayer = CreatePlayer.makePlayer();
        String firstName = mockPlayer.getFirstName();
        String lastName = mockPlayer.getLastName();
        int age = mockPlayer.getAge();
        String email = mockPlayer.getEmail();


        Player test = underTest.addPlayer(mockPlayer);


        Player actual = underTest.getPlayer(test.getId()).get();

        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getAge()).isEqualTo(age);
        assertThat(actual.getEmail()).isEqualTo(email);

    }

    @Test
    void getPlayer_is_Not_Successful() {

    }

    @Test
    void addPlayer() {
        Player mockPlayer = CreatePlayer.makePlayer();
        String firstName = mockPlayer.getFirstName();
        String lastName = mockPlayer.getLastName();
        int age = mockPlayer.getAge();
        String email = mockPlayer.getEmail();

        Player actual = underTest.addPlayer(mockPlayer);


        assertThat(actual.getFirstName()).isEqualTo(firstName);
        assertThat(actual.getLastName()).isEqualTo(lastName);
        assertThat(actual.getAge()).isEqualTo(age);
        assertThat(actual.getEmail()).isEqualTo(email);

    }

    @Test
    void updatePlayer() {
    }

    @Test
    void deletePlayer() {
    }

    @Test
    void deleteAllPlayers() {
    }

    @Test
    void existsPlayerByEmail() {
    }
}