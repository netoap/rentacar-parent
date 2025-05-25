package com.example.auth.ports.out;

import com.example.auth.domain.UserEntity;

public interface SaveUserPort {
    UserEntity save(UserEntity user);
}
