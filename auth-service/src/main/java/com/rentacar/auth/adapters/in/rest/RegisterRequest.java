package com.rentacar.auth.adapters.in.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record RegisterRequest(@NotBlank(message = "Email is required") String email,@NotBlank(message = "Username is required") String username,
                              @NotBlank(message = "Password is required") String password,
                              @Size(min = 1, message = "At least one role must be specified") Set<@NotBlank String> roles) {
}
