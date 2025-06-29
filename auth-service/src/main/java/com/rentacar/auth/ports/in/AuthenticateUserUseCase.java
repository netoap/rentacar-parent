package com.rentacar.auth.ports.in;

import com.rentacar.auth.domain.UserEntity;

import java.util.Optional;

public interface AuthenticateUserUseCase {
    Optional<UserEntity> authenticate(String username, String password);
}