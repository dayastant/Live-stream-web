package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.AdminRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminDTO {
    private String id;
    private String username;
    private AdminRole role;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private Long livestreamCount;

    // Private constructor for builder
    private AdminDTO(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.role = builder.role;
        this.isActive = builder.isActive;
        this.lastLogin = builder.lastLogin;
        this.createdAt = builder.createdAt;
        this.livestreamCount = builder.livestreamCount;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String id;
        private String username;
        private AdminRole role;
        private Boolean isActive;
        private LocalDateTime lastLogin;
        private LocalDateTime createdAt;
        private Long livestreamCount;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder role(AdminRole role) {
            this.role = role;
            return this;
        }

        public Builder isActive(Boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder lastLogin(LocalDateTime lastLogin) {
            this.lastLogin = lastLogin;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder livestreamCount(Long livestreamCount) {
            this.livestreamCount = livestreamCount;
            return this;
        }

        public AdminDTO build() {
            return new AdminDTO(this);
        }
    }

    // Getters
    public String getId() { return id; }
    public String getUsername() { return username; }
    public AdminRole getRole() { return role; }
    public Boolean getIsActive() { return isActive; }
    public LocalDateTime getLastLogin() { return lastLogin; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getLivestreamCount() { return livestreamCount; }

    // Computed fields
    public String getLastLoginFormatted() {
        return lastLogin != null ?
                lastLogin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "Never logged in";
    }

    public String getCreatedAtFormatted() {
        return createdAt != null ?
                createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getRoleDisplay() {
        return role != null ? role.name() : null;
    }
}