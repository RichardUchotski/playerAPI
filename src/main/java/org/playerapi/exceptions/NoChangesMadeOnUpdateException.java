package org.playerapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoChangesMadeOnUpdateException extends RuntimeException {
    public NoChangesMadeOnUpdateException(String message) {
        super(message);
    }
}
