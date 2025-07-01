package com.rentacar.auth.adapters.out.jpa;

import com.rentacar.auth.adapters.in.rest.UserSearchCriteria;
import com.rentacar.auth.adapters.out.jpa.repository.UserRepository;
import com.rentacar.auth.domain.UserEntity;
import com.rentacar.auth.ports.out.LoadUserPort;
import com.rentacar.auth.ports.out.SaveUserPort;
import com.rentacar.auth.ports.out.UpdateUserPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public class JpaUserAdapter implements LoadUserPort, SaveUserPort, UpdateUserPort {

    private final UserRepository userRepository;

    @Autowired
    public JpaUserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<UserEntity> findAll(UserSearchCriteria criteria, Pageable pageable) {
        // Aquí debes implementar la lógica de búsqueda por criterios, usando Specification o QueryDSL
        return userRepository.findAll(pageable); // temporal
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity updatePassword(Long userId, String newEncodedPassword) {
        return null;
    }

    @Override
    public UserEntity updateRoles(Long userId, Set<String> newRoles) {
        return null;
    }

    @Override
    public UserEntity updateStatus(Long userId, boolean enabled) {
        return null;
    }
}
