package org.playerapi.player;

import java.util.List;
import java.util.Optional;

public interface PlayerDAO {
    public List<Player> getPlayers();
    public Optional<Player> getPlayer(int id);
    public Player addPlayer(Player player);
    public void updatePlayer(Player player);
    public void deletePlayer(int id);
    public void deleteAllPlayers();
    public boolean existsPlayerByEmail(String email);
}
