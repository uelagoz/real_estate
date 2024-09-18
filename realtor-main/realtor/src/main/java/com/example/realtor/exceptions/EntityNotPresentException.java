package com.example.realtor.exceptions;

public class EntityNotPresentException extends RuntimeException {
    public EntityNotPresentException(String message) {
        super(message);
    }
}
