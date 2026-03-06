package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.AdminRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminDTO {
    private String id;
    private String username;
    private AdminRole role;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private Long livestreamCount;

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