package com.example.realtor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = { EntityNotPresentException.class })
    public ResponseEntity<GenericError> handleBadRequestExceptions(Exception ex) {
        GenericError genericError = new GenericError();
        genericError.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(genericError);
    }

    @ExceptionHandler(value = { UnauthorizedActionException.class })
    public ResponseEntity<GenericError> handleUnauthorizedExceptions(UnauthorizedActionException ex) {
        GenericError genericError = new GenericError();
        genericError.setMessage(ex.getMessage());
        return new ResponseEntity<>(genericError, HttpStatus.UNAUTHORIZED);
    }

}
