package org.playerapi.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.playerapi.player.PlayerUserDetailService;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final PlayerUserDetailService  playerUserDetailService;

    public JWTAuthenticationFilter(JWTUtil jwtUtil, PlayerUserDetailService playerUserDetailService) {
        this.jwtUtil = jwtUtil;
        this.playerUserDetailService = playerUserDetailService;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }


        String jwtToken = authHeader.substring(7);

        String subject = jwtUtil.getSubject(jwtToken);

        if(subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = playerUserDetailService.loadUserByUsername(subject);
            if(jwtUtil.isTokenValid(jwtToken, userDetails.getUsername())){
                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                 SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        filterChain.doFilter(request, response);


    }
}
