package com.rentacar.auth.adapters.in.rest;

import com.rentacar.auth.domain.UserEntity;
import com.rentacar.auth.ports.out.LoadUserPort;
import com.rentacar.auth.ports.out.UpdateUserPort;
import com.rentacar.commons.dto.PagedResponse;
import com.rentacar.commons.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "User Admin", description = "Admin-only endpoints to manage users")
@RestController
@RequestMapping("/users")
public class UserController {

    private final LoadUserPort loadUserPort;
    private final UpdateUserPort updateUserPort;
    private final PasswordEncoder passwordEncoder;

    public UserController(LoadUserPort loadUserPort, UpdateUserPort updateUserPort, PasswordEncoder passwordEncoder) {
        this.loadUserPort = loadUserPort;
        this.updateUserPort = updateUserPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Get current authenticated user", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        return loadUserPort.findByUsername(username)
                .map(user -> ResponseEntity.ok(
                        new UserProfileResponse(user.getId(), user.getUsername(), new ArrayList<>(user.getRoles()), user.getCreatedAt(), user.isActive()))
                )
                .orElse(ResponseEntity.status(404).build());
    }

    @Operation(
            summary = "Admin-only: Get paginated list of users",
            description = "Supports filtering by username, role, createdAt, and sorting by any field",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @Parameters({
            @Parameter(name = "page", description = "Page number (0-based)", example = "0"),
            @Parameter(name = "size", description = "Number of users per page", example = "10"),
            @Parameter(name = "sort", description = "Sort format: field,direction (e.g., createdAt,desc)", example = "createdAt,desc")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<PagedResponse<UserProfileResponse>> getAllUsers(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @ModelAttribute UserSearchCriteria criteria,
            @Parameter(description = "Sort format: field,direction") @RequestParam(defaultValue = "createdAt,desc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sort[1]), sort[0]));

        Page<UserEntity> userPage = loadUserPort.findAll(criteria, pageable);

        List<UserProfileResponse> userProfiles = userPage.getContent().stream()
                .map(user -> new UserProfileResponse(user.getId(), user.getUsername(), new ArrayList<>(user.getRoles()), user.getCreatedAt(), user.isActive()))
                .toList();

        PagedResponse<UserProfileResponse> response = new PagedResponse<>(
                page,
                size,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userProfiles
        );

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Change user password (ADMIN only)",
            description = "Allows admin to reset a user's password",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/change-password")
    public ResponseEntity<UserProfileResponse> changePassword(
            @PathVariable Long id,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        String encoded = passwordEncoder.encode(request.getNewPassword());
        UserEntity user = updateUserPort.updatePassword(id, encoded);
        return ResponseEntity.ok(new UserProfileResponse(user.getId(), user.getUsername(), new ArrayList<>(user.getRoles()), user.getCreatedAt(), user.isActive()));
    }


    @Operation(summary = "Update user roles", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/roles")
    public ResponseEntity<UserProfileResponse> updateRoles(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRolesRequest request
    ) {
        UserEntity updated = updateUserPort.updateRoles(id, request.getRoles());
        return ResponseEntity.ok(new UserProfileResponse(updated.getId(), updated.getUsername(), new ArrayList<>(updated.getRoles()), updated.getCreatedAt(), updated.isActive()));
    }


    @Operation(summary = "Enable or disable user", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<UserProfileResponse> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request
    ) {
        UserEntity updated = updateUserPort.updateStatus(id, request.isEnabled());
        return ResponseEntity.ok(new UserProfileResponse(updated.getId(), updated.getUsername(), new ArrayList<>(updated.getRoles()), updated.getCreatedAt(), updated.isActive()));
    }

    @Operation(summary = "Get user by ID", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        return loadUserPort.findById(id)
                .map(user -> ResponseEntity.ok(new UserProfileResponse(
                        user.getId(),
                        user.getUsername(),
                        new ArrayList<>(user.getRoles()),
                        user.getCreatedAt(),
                        user.isActive()
                )))
                .orElse(ResponseEntity.notFound().build());
    }


}
