package org.playerapi.player;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerJPARepository extends JpaRepository<Player, Integer> {
    boolean existsByEmail(String email);
    Optional<Player> findByEmail(String email);
}
