package com.rentacar.auth.domain.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String name) {
        super("An account with this name '" + name + "' already exists.");
    }
}
