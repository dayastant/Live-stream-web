package com.example.Live.stream.service.mapper;

import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.service.dto.AdminDTO;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {

    public AdminDTO toDto(Admin admin) {
        if (admin == null) return null;

        return AdminDTO.builder()  // This will now work with manual builder
                .id(admin.getId())
                .username(admin.getUsername())
                .role(admin.getRole())
                .isActive(admin.getIsActive())
                .lastLogin(admin.getLastLogin())
                .createdAt(admin.getCreatedAt())
                .livestreamCount(admin.getLivestreams() != null ?
                        (long) admin.getLivestreams().size() : 0L)
                .build();
    }

    public Admin toEntity(AdminDTO dto) {
        if (dto == null) return null;
        Admin admin = new Admin();
        admin.setId(dto.getId());
        admin.setUsername(dto.getUsername());
        admin.setRole(dto.getRole());
        admin.setIsActive(dto.getIsActive() != null ? dto.getIsActive() : true);
        admin.setLastLogin(dto.getLastLogin());
        // Note: passwordHash should be set separately for security
        return admin;
    }

    public void updateEntity(AdminDTO dto, Admin admin) {
        if (dto == null || admin == null) return;

        if (dto.getUsername() != null) admin.setUsername(dto.getUsername());
        if (dto.getRole() != null) admin.setRole(dto.getRole());
        if (dto.getIsActive() != null) admin.setIsActive(dto.getIsActive());
        if (dto.getLastLogin() != null) admin.setLastLogin(dto.getLastLogin());
    }

    public AdminDTO toSimpleDto(Admin admin) {
        if (admin == null) return null;

        return AdminDTO.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .role(admin.getRole())
                .build();
    }
}