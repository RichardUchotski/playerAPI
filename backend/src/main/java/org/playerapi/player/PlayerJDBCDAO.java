package org.playerapi.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
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
        Optional<Player> playerOptional = jdbcTemplate.query(sql, playerRowMapper).stream().filter(p -> p.getId() == id).findFirst();
        playerOptional.ifPresent(p -> {
            System.out.println(p.getPassword());
        });
        return playerOptional;
    }

    @Override
    public Player addPlayer(Player player) {
        String sql ="INSERT into player(first_name,last_name,age,date_of_birth,phone_number,email,password,gender,team,terms_accepted) values(?,?,?,?,?,?,?,?,?,?)";

        // Create a KeyHolder to store the generated ID
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // Execute the update and capture the generated ID
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] {"id"});
            ps.setString(1, player.getFirstName());
            ps.setString(2, player.getLastName());
            ps.setInt(3, player.getAge());
            ps.setDate(4, Date.valueOf(player.getDateOfBirth()));
            ps.setString(5, player.getPhoneNumber());
            ps.setString(6, player.getEmail());
            ps.setString(7, player.getPassword());
            ps.setString(8, player.getGender().toString());
            ps.setString(9, player.getTeam());
            ps.setBoolean(10, player.isTermsAccept());
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
        var sql = "UPDATE player SET first_name = ?, last_name = ?, age = ?, date_of_birth= ?, phone_number = ?,  email = ?, password= ?, gender = ?, team = ?, terms_accepted = ? WHERE id = ?";
        jdbcTemplate.update(sql, player.getFirstName(), player.getLastName(), player.getAge(), player.getDateOfBirth(), player.getPhoneNumber(), player.getEmail(), player.getPassword(),player.getGender().toString(), player.getTeam(), player.isTermsAccept(), id);
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

    @Override
    public Optional<Player> getPlayerByEmail(String email) {
        var sql = "SELECT * FROM player WHERE email = ?";
        return jdbcTemplate.query(sql,playerRowMapper,email).stream().findFirst();
    }

}
