package com.rentacar.auth.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String username) {
        super("User with username '" + username + "' was not found.");
    }
}
