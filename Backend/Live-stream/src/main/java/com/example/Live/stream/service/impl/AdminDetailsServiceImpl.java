package com.example.Live.stream.service.impl;

import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.repository.AdminRepository;
import com.example.Live.stream.service.AdminDetailsService;
import com.example.Live.stream.service.validation.AdminValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@Transactional
public class AdminDetailsServiceImpl implements AdminDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AdminDetailsServiceImpl.class);

    private final AdminRepository adminRepository;
    private final AdminValidator adminValidator;
    private final PasswordEncoder passwordEncoder;

    // Constructor injection (no circular dependency)
    public AdminDetailsServiceImpl(
            AdminRepository adminRepository,
            AdminValidator adminValidator,
            PasswordEncoder passwordEncoder
    ) {
        this.adminRepository = adminRepository;
        this.adminValidator = adminValidator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user details for username: {}", username);

        // Validate input
        adminValidator.validateUsernameForAuthentication(username);

        // Fetch admin
        Admin admin = findAdminByUsername(username);
        validateAdminStatus(admin);

        // Build Spring Security UserDetails
        return buildUserDetails(admin);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin loadAdminByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading admin entity for username: {}", username);

        Admin admin = findAdminByUsername(username);
        validateAdminStatus(admin);

        return admin;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        log.debug("Checking if username exists: {}", username);
        return adminRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateCredentials(String username, String password) {
        log.debug("Validating credentials for username: {}", username);

        try {
            Admin admin = findAdminByUsername(username);
            validateAdminStatus(admin);

            // Compare raw password with encoded password
            return passwordEncoder.matches(password, admin.getPasswordHash());
        } catch (UsernameNotFoundException e) {
            log.warn("Validation failed - username not found: {}", username);
            return false;
        }
    }

    @Override
    @Transactional
    public void updateLastLogin(String username) {
        log.debug("Updating last login for username: {}", username);

        Admin admin = findAdminByUsername(username);
        admin.setLastLogin(LocalDateTime.now());
        adminRepository.save(admin);

        log.info("Last login updated for admin: {}", username);
    }

    @Override
    @Transactional(readOnly = true)
    public String getAdminRole(String username) {
        log.debug("Getting role for username: {}", username);

        Admin admin = findAdminByUsername(username);
        return admin.getRole().name();
    }

    // -------------------- Private helper methods --------------------

    private Admin findAdminByUsername(String username) {
        return adminRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.warn("Admin not found with username: {}", username);
                    return new UsernameNotFoundException(
                            String.format("Admin not found with username: %s", username)
                    );
                });
    }

    private void validateAdminStatus(Admin admin) {
        if (!admin.getIsActive()) {
            log.warn("Admin account is deactivated: {}", admin.getUsername());
            throw new UsernameNotFoundException(
                    String.format("Admin account is deactivated: %s", admin.getUsername())
            );
        }
    }

    private UserDetails buildUserDetails(Admin admin) {
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPasswordHash())
                .authorities(Collections.singletonList(
                        new SimpleGrantedAuthority("ROLE_" + admin.getRole().name())
                ))
                .accountLocked(!admin.getIsActive())
                .disabled(!admin.getIsActive())
                .build();
    }
}