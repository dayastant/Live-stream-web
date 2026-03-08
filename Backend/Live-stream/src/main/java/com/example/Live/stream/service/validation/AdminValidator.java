package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.service.dto.AdminDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

@Component
public class AdminValidator {

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._-]{3,50}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public void validateForCreation(AdminDTO adminDTO, String password) {
        validateAdminDTO(adminDTO);
        validatePassword(password);
    }

    public void validateForUpdate(AdminDTO adminDTO, Admin existingAdmin) {
        if (adminDTO == null) return;

        if (adminDTO.getUsername() != null &&
                !adminDTO.getUsername().equals(existingAdmin.getUsername())) {
            throw new IllegalArgumentException("Username cannot be changed");
        }

        if (adminDTO.getRole() != null &&
                existingAdmin.getRole() != adminDTO.getRole() &&
                !existingAdmin.isSuperAdmin()) {
            throw new IllegalArgumentException("Only super admin can change roles");
        }
    }

    private void validateAdminDTO(AdminDTO adminDTO) {
        if (adminDTO == null) {
            throw new IllegalArgumentException("Admin data cannot be null");
        }

        // Validate username
        if (!StringUtils.hasText(adminDTO.getUsername())) {
            throw new IllegalArgumentException("Username is required");
        }

        if (!USERNAME_PATTERN.matcher(adminDTO.getUsername()).matches()) {
            throw new IllegalArgumentException(
                    "Username must be 3-50 characters and can only contain letters, numbers, dots, underscores and hyphens"
            );
        }

        // Validate role
        if (adminDTO.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    private void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password is required");
        }

        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long and contain at least one digit, " +
                            "one lowercase, one uppercase letter, and one special character (@#$%^&+=)"
            );
        }
    }

    public void validateLogin(String username, String password) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username is required");
        }

        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password is required");
        }
    }

    public void validatePasswordChange(String oldPassword, String newPassword) {
        if (!StringUtils.hasText(oldPassword)) {
            throw new IllegalArgumentException("Old password is required");
        }

        validatePassword(newPassword);

        if (oldPassword.equals(newPassword)) {
            throw new IllegalArgumentException("New password must be different from old password");
        }
    }

    public void validateEmail(String email) {
        if (email != null && !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}