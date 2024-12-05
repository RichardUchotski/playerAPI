package org.playerapi.player;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerJPARepository extends JpaRepository<Player, Integer> {
    boolean existsByEmail(String email);
}
