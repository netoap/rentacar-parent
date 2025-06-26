package com.example.auth.ports.out;

import com.example.auth.domain.UserEntity;

import java.util.Set;

public interface UpdateUserPort {
    UserEntity updatePassword(Long userId, String newEncodedPassword);
    UserEntity updateRoles(Long userId, Set<String> newRoles);
    UserEntity updateStatus(Long userId, boolean enabled);
}
