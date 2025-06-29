package com.rentacar.auth.adapters.in.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Schema(description = "Search criteria for filtering users")
public class UserSearchCriteria {

    @Schema(description = "Filter by username (contains)")
    private String username;

    @Schema(description = "Filter by role, e.g., ROLE_ADMIN or ROLE_USER")
    private String role;

    @Schema(description = "Created after (YYYY-MM-DD)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtFrom;

    @Schema(description = "Created before (YYYY-MM-DD)")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createdAtTo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(LocalDate createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public LocalDate getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(LocalDate createdAtTo) {
        this.createdAtTo = createdAtTo;
    }
}
