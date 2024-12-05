package org.playerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlayerByIdNotInDatabaseException extends RuntimeException {
    public PlayerByIdNotInDatabaseException(String message) {
        super(message);
    }
}
