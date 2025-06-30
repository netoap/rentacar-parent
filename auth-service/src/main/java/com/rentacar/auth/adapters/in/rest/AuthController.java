package com.rentacar.auth.adapters.in.rest;

import com.rentacar.auth.application.AuthService;
import com.rentacar.auth.config.security.JwtTokenProvider;
import com.rentacar.auth.domain.UserEntity;
import com.rentacar.auth.ports.in.AuthenticateUserUseCase;
import com.rentacar.commons.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticateUserUseCase authenticateUserUseCase;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(AuthenticateUserUseCase authenticateUserUseCase, AuthService authService, JwtTokenProvider jwtTokenProvider) {
        this.authenticateUserUseCase = authenticateUserUseCase;
        this.authService = authService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Operation(summary = "Authenticate user and return JWT")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return authenticateUserUseCase.authenticate(request.username(), request.password()).<ResponseEntity<?>>map(user -> {
            String token = jwtTokenProvider.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token));
        }).orElseGet(() -> ResponseEntity.status(401).body(new ErrorResponse(401, "Unauthorized", "Invalid credentials")));
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserProfileResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserEntity user = authService.register(request.email(), request.username(), request.password(), request.roles());

        URI location = URI.create("/users/" + user.getId());

        return ResponseEntity.created(location)
                .body(new UserProfileResponse(
                        user.getId(),
                        user.getUsername(),
                        new ArrayList<>(user.getRoles()),
                        user.getCreatedAt(),
                        user.isActive()
                ));
    }

    @Operation(summary = "Get current authenticated user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> me(Authentication authentication) {
        String username = authentication.getName();
        UserEntity user = authService.findByUsername(username);

        return ResponseEntity.ok(new UserProfileResponse(
                user.getId(),
                user.getUsername(),
                new ArrayList<>(user.getRoles()),
                user.getCreatedAt(),
                user.isActive()
        ));
    }
}
