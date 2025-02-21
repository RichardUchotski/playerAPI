package org.playerapi.login;

import org.playerapi.player.PlayerDTO;


public record AuthenticationResponse(String token, PlayerDTO playerDTO)  {
}
