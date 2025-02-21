package org.playerapi.player;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PlayerUserDetailService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final PlayerDAO playerDAO;

    public PlayerUserDetailService(@Qualifier("jpa") PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return playerDAO.getPlayerByEmail(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
