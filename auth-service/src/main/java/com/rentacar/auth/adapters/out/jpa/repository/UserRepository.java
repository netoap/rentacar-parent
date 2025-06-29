package com.rentacar.auth.adapters.out.jpa.repository;

import com.rentacar.auth.domain.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity save(UserEntity user);
    Optional<UserEntity> findByEmail(String email);
}
