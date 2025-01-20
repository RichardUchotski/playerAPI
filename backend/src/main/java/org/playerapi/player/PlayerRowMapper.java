package org.playerapi.player;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PlayerRowMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
        return  new Player(
                rs.getInt("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getInt("age"),
                rs.getDate("date_of_birth").toLocalDate(),
                rs.getString("phone_number"),
                rs.getString("email"),
                Gender.valueOf(rs.getString("gender")),
                rs.getString("team"),
                rs.getBoolean("terms_accepted")
        );
    }
}
