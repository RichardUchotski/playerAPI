package org.playerapi.login;

import org.playerapi.jwt.JWTUtil;
import org.playerapi.player.Player;
import org.playerapi.player.PlayerDTOMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final PlayerDTOMapper playerDTOMapper;

    public AuthenticationService(AuthenticationManager authenticationManager, JWTUtil jwtUtil, PlayerDTOMapper playerDTOMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.playerDTOMapper = playerDTOMapper;
    }


    @Override
    public AuthenticationResponse login(LoginRequestDTO loginRequest) {

        Authentication authenticateObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.username(), loginRequest.password()
        ));

        SecurityContextHolder.getContext().setAuthentication(authenticateObject);
        Player player = (Player) authenticateObject.getPrincipal();
        String jwtToken = jwtUtil.issueToken(loginRequest.username(), player.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return new AuthenticationResponse(jwtToken, playerDTOMapper.apply(player));
    }
}
