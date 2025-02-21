package org.playerapi.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class JWTUtil {
    public String issueToken(String subject,  Map<String,Object> claims){
        return Jwts.builder()
                .claims(claims).issuer("https//playerapi.io")
                .subject(subject)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now()
                .plus(15, ChronoUnit.DAYS)))
                .signWith(getSecretKey())
                .compact();
    }

    public String issueToken(String subject){
        return issueToken(subject,Map.of());
    }

    @SafeVarargs
    public final String issueToken(String subject, List<String>... scopes){
        return issueToken(subject,Map.of("scopes",scopes));
    }

    public String issueToken(String subject, String... scopes){
        return issueToken(subject,Map.of("scopes",scopes));
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor("omgThisIsMYSecretKeyomgThisIsMYSecretKeyomgThisIsMYSecretKeyomgThisIsMYSecretKey".getBytes());
    }

    public String getSubject(String token){
        Claims payload = getClaims(token);
        return payload.getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String jwtToken, String username) {
        String subject = getSubject(jwtToken);
        return subject.equals(username) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return getClaims(jwtToken).getExpiration().before(Date.from(Instant.now()));
    }
}
