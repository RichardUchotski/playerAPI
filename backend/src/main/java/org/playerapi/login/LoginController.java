package org.playerapi.login;

import org.playerapi.player.PlayerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth/login")
public class LoginController {


    private final AuthenticationService authenticationService;

    @Autowired
    public LoginController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest){
        AuthenticationResponse login = authenticationService.login(loginRequest);
        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, login.token()).body(login);

    }



}
