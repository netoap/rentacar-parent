package com.rentacar.auth.ports.out;

import com.rentacar.auth.domain.UserEntity;

public interface SaveUserPort {
    UserEntity save(UserEntity user);
}
