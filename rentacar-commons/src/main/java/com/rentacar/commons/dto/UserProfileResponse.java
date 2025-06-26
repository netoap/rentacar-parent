package com.rentacar.commons.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record UserProfileResponse(
        Long id,
        String username,
        Set<String> roles,
        LocalDateTime createdAt,
        boolean enabled
) {}
