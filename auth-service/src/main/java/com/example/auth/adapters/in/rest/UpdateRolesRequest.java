package com.example.auth.adapters.in.rest;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public class UpdateRolesRequest {

    @NotEmpty(message = "At least one role must be provided")
    private List<String> roles;

    public List<String> getRoles() { return roles; }
    public void setRoles(List<String> roles) { this.roles = roles; }
}
