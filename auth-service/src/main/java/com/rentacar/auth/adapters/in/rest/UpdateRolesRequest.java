package com.rentacar.auth.adapters.in.rest;

import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public class UpdateRolesRequest {

    @NotEmpty(message = "At least one role must be provided")
    private Set<String> roles;

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
