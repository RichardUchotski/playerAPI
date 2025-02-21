package org.playerapi.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerByIdNotInDatabaseException.class)
    public ResponseEntity<ApiError> playerByIdNotInDatabaseException(PlayerByIdNotInDatabaseException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), e.getMessage(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getLocalizedMessage(), e.getCause(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> badCredentialsException(BadCredentialsException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), e.getMessage(), HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(),  e.getLocalizedMessage(), e.getCause(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(apiError);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> insufficientAuthenticationException(InsufficientAuthenticationException e, HttpServletRequest request) {
        ApiError apiError = new ApiError(request.getRequestURI(), e.getMessage(), HttpStatus.FORBIDDEN.value(), LocalDateTime.now(),  e.getLocalizedMessage(), e.getCause(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(apiError);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> exception(Exception e, HttpServletRequest request) {
        e.printStackTrace();
        ApiError apiError = new ApiError(request.getRequestURI(), e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),  e.getLocalizedMessage(), e.getCause(), e.getStackTrace());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}
