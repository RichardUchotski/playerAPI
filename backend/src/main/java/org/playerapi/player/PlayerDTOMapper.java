package org.playerapi.player;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PlayerDTOMapper implements Function<Player, PlayerDTO> {
    @Override
    public PlayerDTO apply(Player player) {
        return new PlayerDTO(
                player.getId(),
                player.getFirstName(),
                player.getLastName(),
                player.getAge(),
                player.getDateOfBirth(),
                player.getPhoneNumber(),
                player.getEmail(),
                player.getGender(),
                player.getTeam(),
                player.isTermsAccepted(),
                player.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList(),
                player.getUsername());
    }
}
