package org.playerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoPlayersInDatabaseException extends RuntimeException {
    public NoPlayersInDatabaseException() {
        super("No Players in the database.");
    }
}
