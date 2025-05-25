package com.example.auth.adapters.in.rest;

import com.example.auth.application.AuthService;
import com.example.auth.config.security.JwtTokenProvider;
import com.example.auth.domain.UserEntity;
import com.example.auth.ports.in.AuthenticateUserUseCase;
import com.rentacar.commons.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth")
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
            String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRoles());
            return ResponseEntity.ok(Map.of("token", token));
        }).orElseGet(() -> ResponseEntity.status(401).body(new ErrorResponse(401, "Unauthorized", "Invalid credentials")));
    }

    @Operation(summary = "Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserProfileResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserEntity user = authService.register(request.username(), request.password(), request.roles());

        URI location = URI.create("/users/" + user.getId());

        return ResponseEntity.created(location)
                .body(new UserProfileResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getRoles(),
                        user.getCreatedAt(),
                        user.isActive()
                ));
    }

    @Operation(summary = "Get current authenticated user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<?> me(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(Map.of("username", username));
    }
}
