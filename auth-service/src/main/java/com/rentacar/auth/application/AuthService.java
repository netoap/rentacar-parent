package com.rentacar.auth.application;

import com.rentacar.auth.domain.UserEntity;
import com.rentacar.auth.domain.exception.EmailAlreadyExistsException;
import com.rentacar.auth.domain.exception.UserNotFoundException;
import com.rentacar.auth.domain.exception.UsernameAlreadyExistsException;
import com.rentacar.auth.ports.in.AuthenticateUserUseCase;
import com.rentacar.auth.ports.out.LoadUserPort;
import com.rentacar.auth.ports.out.SaveUserPort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthService implements AuthenticateUserUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveUserPort saveUserPort;
    private final PasswordEncoder passwordEncoder;

    public AuthService(LoadUserPort loadUserPort, SaveUserPort saveUserPort, PasswordEncoder passwordEncoder) {
        this.loadUserPort = loadUserPort;
        this.saveUserPort = saveUserPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserEntity> authenticate(String username, String password) {
        UserEntity user = loadUserPort.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!password.equals(user.getPassword())) {
            return Optional.empty();  // Wrong password = 401 handled in controller
        }
        return Optional.of(user);
    }


    public UserEntity register(String email, String username, String password, Set<String> roles) {
        if (loadUserPort.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }
        if (loadUserPort.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        //String encodedPassword = passwordEncoder.encode(password);
        UserEntity newUser = new UserEntity(email, username, password, roles);
        return saveUserPort.save(newUser); // you'd need a SaveUserPort + adapter for this
    }

    public UserEntity findByUsername(String username) {
        return loadUserPort.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
