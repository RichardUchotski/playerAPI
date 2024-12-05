package org.playerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlayerAlreadyExistsByEmailException extends RuntimeException {
    public PlayerAlreadyExistsByEmailException(String message) {
        super(message);
    }
}
