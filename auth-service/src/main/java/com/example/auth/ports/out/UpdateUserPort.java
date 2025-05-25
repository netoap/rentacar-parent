package com.example.auth.ports.out;

import com.example.auth.domain.UserEntity;

public interface UpdateUserPort {
    UserEntity updatePassword(Long userId, String newEncodedPassword);
    UserEntity updateRoles(Long userId, java.util.List<String> newRoles);
    UserEntity updateStatus(Long userId, boolean enabled);
}
