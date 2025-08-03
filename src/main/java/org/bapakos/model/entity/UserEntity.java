package org.bapakos.model.entity;

import java.time.LocalDateTime;

public class UserEntity {
    private String id;
    private String username;
    private String password;
    private Role role;
    private LocalDateTime createdAt;

    public enum Role {
        pemilik,
        penyewa
    }

    public UserEntity() {

    }

    public UserEntity(String id, String username, String password, Role role, LocalDateTime createdAt) {
        this.setId(id);
        this.setUsername(username);
        this.setPassword(password);
        this.setRole(role);
        this.setCreatedAt(createdAt);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
