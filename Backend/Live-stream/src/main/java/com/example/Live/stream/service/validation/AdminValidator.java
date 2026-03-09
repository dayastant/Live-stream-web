package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.security.dto.AdminLoginRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Validator for admin-related operations.
 * Follows Single Responsibility Principle - only handles validation.
 */
@Component
public class AdminValidator {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 50;
    private static final int MIN_PASSWORD_LENGTH = 8;

    /**
     * Validates username for authentication process.
     *
     * @param username the username to validate
     * @throws IllegalArgumentException if username is invalid
     */
    public void validateUsernameForAuthentication(String username) {
        if (!StringUtils.hasText(username)) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        if (username.length() < MIN_USERNAME_LENGTH || username.length() > MAX_USERNAME_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Username must be between %d and %d characters",
                            MIN_USERNAME_LENGTH, MAX_USERNAME_LENGTH)
            );
        }

        // Validate username format (alphanumeric and allowed special characters)
        if (!username.matches("^[a-zA-Z0-9._-]+$")) {
            throw new IllegalArgumentException(
                    "Username can only contain letters, numbers, dots, underscores and hyphens"
            );
        }
    }

    /**
     * Validates admin status.
     *
     * @param admin the admin to validate
     * @throws IllegalStateException if admin is inactive
     */
    public void validateAdminActive(Admin admin) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }

        if (!admin.getIsActive()) {
            throw new IllegalStateException(
                    String.format("Admin account is deactivated: %s", admin.getUsername())
            );
        }
    }

    /**
     * Validates login request.
     *
     * @param request the login request
     * @throws IllegalArgumentException if request is invalid
     */
    public void validateLoginRequest(AdminLoginRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Login request cannot be null");
        }

        validateUsernameForAuthentication(request.getUsername());
        validatePassword(request.getPassword());
    }

    /**
     * Validates password.
     *
     * @param password the password to validate
     * @throws IllegalArgumentException if password is invalid
     */
    public void validatePassword(String password) {
        if (!StringUtils.hasText(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }

        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException(
                    String.format("Password must be at least %d characters long", MIN_PASSWORD_LENGTH)
            );
        }
    }

    /**
     * Validates admin for creation.
     *
     * @param admin the admin to validate
     * @param rawPassword the raw password
     * @throws IllegalArgumentException if validation fails
     */
    public void validateForCreation(Admin admin, String rawPassword) {
        if (admin == null) {
            throw new IllegalArgumentException("Admin cannot be null");
        }

        validateUsernameForAuthentication(admin.getUsername());
        validatePassword(rawPassword);

        if (admin.getRole() == null) {
            throw new IllegalArgumentException("Admin role cannot be null");
        }
    }
}