package org.playerapi.login;

public interface AuthService {
    AuthenticationResponse login(LoginRequestDTO loginRequest);
}
