package org.playerapi.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jpa")
public class PlayerJPADAO implements PlayerDAO{

    PlayerJPARepository playerJPARepository;

    @Autowired
    public PlayerJPADAO(PlayerJPARepository playerJPARepository) {
        this.playerJPARepository = playerJPARepository;
    }

    @Override
    public List<Player> getPlayers() {
        return playerJPARepository.findAll();
    }

    @Override
    public Optional<Player> getPlayer(int id) {
        return playerJPARepository.findById(id);
    }

    @Override
    public Player addPlayer(Player player) {
        playerJPARepository.save(player);
        return player;
    }

    @Override
    public void updatePlayer(Player player) {
        playerJPARepository.save(player);
    }

    @Override
    public void deletePlayer(int id) {
        Optional<Player> playerToDelete = getPlayer(id);
        playerToDelete.ifPresent(player -> playerJPARepository.delete(player));
    }

    @Override
    public void deleteAllPlayers() {
        playerJPARepository.deleteAll();
    }

    @Override
    public boolean existsPlayerByEmail(String email) {
        return playerJPARepository.existsByEmail(email);
    }
}
