package com.example.Live.stream.domain.entity.admin;


import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.entity.livestream.Livestream;
import com.example.Live.stream.domain.enums.AdminRole;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = {"livestreams", "passwordHash"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Admin extends BaseEntity {

    @Column(nullable = false, unique = true, length = 50)
    @EqualsAndHashCode.Include
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private AdminRole role = AdminRole.SUPER_ADMIN;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Livestream> livestreams = new ArrayList<>();

    // Business methods
    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public boolean isSuperAdmin() {
        return AdminRole.SUPER_ADMIN.equals(this.role);
    }
}