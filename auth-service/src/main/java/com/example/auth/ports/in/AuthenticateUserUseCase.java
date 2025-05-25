package com.example.auth.ports.in;

import com.example.auth.domain.UserEntity;

import java.util.Optional;

public interface AuthenticateUserUseCase {
    Optional<UserEntity> authenticate(String username, String password);
}