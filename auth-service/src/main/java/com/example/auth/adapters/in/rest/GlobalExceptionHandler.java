package com.example.auth.adapters.in.rest;

import com.example.auth.domain.exception.EmailAlreadyExistsException;
import com.example.auth.domain.exception.UserNotFoundException;
import com.example.auth.domain.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {



    //validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(400, "Bad Request", "Validation failed"),
                HttpStatus.BAD_REQUEST
        );
    }

    //	login failure
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(401, "Unauthorized", ex.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    //login, user lookup
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(404, "Not Found", ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    //register conflict
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUsernameExists(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(409, "Conflict", ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    //register conflict
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(409, "Conflict", ex.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    //	fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return new ResponseEntity<>(
                new ErrorResponse(500, "Internal Server Error", ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
