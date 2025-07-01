package com.rentacar.commons.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserProfileResponse(
        Long id,
        String username,
        String email,
        List<String> roles,
        LocalDateTime createdAt,
        boolean enabled
) {}
