package com.example.auth.application;

import com.example.auth.domain.UserEntity;
import com.example.auth.domain.exception.EmailAlreadyExistsException;
import com.example.auth.domain.exception.UserNotFoundException;
import com.example.auth.domain.exception.UsernameAlreadyExistsException;
import com.example.auth.ports.in.AuthenticateUserUseCase;
import com.example.auth.ports.out.LoadUserPort;
import com.example.auth.ports.out.SaveUserPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
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

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return Optional.empty();  // Wrong password = 401 handled in controller
        }

        return Optional.of(user);
    }

    public UserEntity register(String email, String username, String password, Set<String> roles) {
        if (loadUserPort.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException(username);
        }

        String encodedPassword = passwordEncoder.encode(password);
        UserEntity newUser = new UserEntity(email, username, encodedPassword, roles);
        return saveUserPort.save(newUser); // you'd need a SaveUserPort + adapter for this
    }
}
