package com.example.auth.adapters.in.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record RegisterRequest(@NotBlank(message = "Username is required") String username,
                              @NotBlank(message = "Password is required") String password,
                              @Size(min = 1, message = "At least one role must be specified") List<@NotBlank String> roles) {
}
