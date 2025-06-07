package com.example.auth.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 100)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles;
    private boolean active = true;
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    public UserEntity() {}

    public UserEntity(String email, String username, String password, List<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public List<String> getRoles() { return roles; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setPassword(String password) { this.password = password; }

    public boolean isActive() {
        return active;
    }

    public void setRoles(List<String> newRoles) {
        this.roles=newRoles;
    }

    public String getEmail() {
        return email;
    }
}
