package com.example.customer.adapters.in.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for creating a new customer")
public class CustomerRequest {

    @NotBlank(message = "Name is required")
    @Schema(description = "Full name of the customer", example = "Alice Smith", required = true)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Email address of the customer", example = "alice@example.com", required = true)
    private String email;

    public CustomerRequest() {}

    public CustomerRequest(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
