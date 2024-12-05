package org.playerapi.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository("jdbc")
public class PlayerJDBCDAO implements PlayerDAO {

    JdbcTemplate jdbcTemplate;
    PlayerRowMapper playerRowMapper;

    @Autowired
    public PlayerJDBCDAO(JdbcTemplate jdbcTemplate, PlayerRowMapper playerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.playerRowMapper = playerRowMapper;
    }

    @Override
    public List<Player> getPlayers() {
        var sql = "SELECT * FROM player";
        return jdbcTemplate.query(sql,playerRowMapper);
    }

    @Override
    public Optional<Player> getPlayer(int id) {
        var sql = "SELECT * FROM player";
        return jdbcTemplate.query(sql,playerRowMapper).stream().filter(p -> p.getId() == id).findFirst();
    }

    @Override
    public Player addPlayer(Player player) {
        String sql = "INSERT INTO player(first_name, last_name, age, email) VALUES(?,?,?,?)";

        // Create a KeyHolder to store the generated ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Execute the update and capture the generated ID
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, player.getFirstName());
            ps.setString(2, player.getLastName());
            ps.setInt(3, player.getAge());
            ps.setString(4, player.getEmail());
            return ps;
        }, keyHolder);

        // Retrieve the generated ID and set it on the player object
        int generatedId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        player.setId(generatedId);

        return player; // Return the updated player object
    }



    @Override
    public void updatePlayer(Player player) {
        int id = player.getId();
        var sql = "UPDATE player SET first_name = ?, last_name = ?, age = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, player.getFirstName(), player.getLastName(), player.getAge(), player.getEmail(), id);
    }


    @Override
    public void deletePlayer(int id) {
        jdbcTemplate.update("DELETE FROM player WHERE id = ?",id);

    }

    @Override
    public void deleteAllPlayers() {
        jdbcTemplate.update("DELETE FROM player");
    }

    @Override
    public boolean existsPlayerByEmail(String email) {
        var sql = "SELECT COUNT(*) FROM player WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }

}
