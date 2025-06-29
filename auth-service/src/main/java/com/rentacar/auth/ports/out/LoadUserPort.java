package com.rentacar.auth.ports.out;

import com.rentacar.auth.adapters.in.rest.UserSearchCriteria;
import com.rentacar.auth.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface LoadUserPort {
    Optional<UserEntity> findByUsername(String username);
    Page<UserEntity> findAll(Pageable pageable);
    Page<UserEntity> findAll(UserSearchCriteria criteria, Pageable pageable);
    Optional<UserEntity> findById(Long id);
    boolean existsByEmail(String email);
}
