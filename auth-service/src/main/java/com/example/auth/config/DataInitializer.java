package com.example.auth.config;

import com.example.auth.adapters.out.jpa.repository.UserRepository;
import com.example.auth.domain.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            userRepository.findByUsername("admin").ifPresentOrElse(
                    u -> {}, // Do nothing if admin exists
                    () -> {
                        UserEntity admin = new UserEntity(
                                "admin@rentacar.com",
                                "admin",
                                passwordEncoder.encode("admin123"),
                                Set.of("ROLE_ADMIN")
                        );
                        userRepository.save(admin);
                    }
            );
        };
    }
}
