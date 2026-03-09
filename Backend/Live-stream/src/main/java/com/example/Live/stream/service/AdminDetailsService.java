package com.example.Live.stream.service;

import com.example.Live.stream.domain.entity.admin.Admin;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Interface for admin-specific user details service.
 * Follows Interface Segregation Principle - extends base interface with admin-specific methods.
 */
public interface AdminDetailsService extends UserDetailsService {

    /**
     * Loads admin entity by username.
     *
     * @param username the username
     * @return Admin entity
     * @throws UsernameNotFoundException if admin not found
     */
    Admin loadAdminByUsername(String username) throws UsernameNotFoundException;

    /**
     * Updates admin last login time.
     *
     * @param username the username
     */
    void updateLastLogin(String username);

    /**
     * Gets admin role.
     *
     * @param username the username
     * @return role name
     */
    String getAdminRole(String username);

    /**
     * Checks if username exists.
     *
     * @param username the username
     * @return true if exists
     */
    boolean existsByUsername(String username);

    /**
     * Validates admin credentials.
     *
     * @param username the username
     * @param password the password
     * @return true if valid
     */
    boolean validateCredentials(String username, String password);
}