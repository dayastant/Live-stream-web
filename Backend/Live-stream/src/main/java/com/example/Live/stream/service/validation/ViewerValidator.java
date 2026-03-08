package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.livestream.Livestream;
import com.example.Live.stream.domain.entity.viewer.ViewerSession;
import com.example.Live.stream.service.dto.ViewerSessionDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.util.regex.Pattern;

@Component
public class ViewerValidator {

    private static final Pattern IPV4_PATTERN =
            Pattern.compile("^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$");

    private static final int MAX_DEVICE_INFO_LENGTH = 500;
    private static final int MAX_SESSION_DURATION_HOURS = 24;

    public void validateSessionCreation(ViewerSessionDTO sessionDTO) {
        if (sessionDTO == null) {
            throw new IllegalArgumentException("Session data cannot be null");
        }

        // Session token should be generated, not provided
        if (StringUtils.hasText(sessionDTO.getSessionToken())) {
            throw new IllegalArgumentException("Session token should not be provided");
        }

        // Device info validation
        if (sessionDTO.getDeviceInfo() != null) {
            if (sessionDTO.getDeviceInfo().length() > MAX_DEVICE_INFO_LENGTH) {
                throw new IllegalArgumentException(
                        "Device info too long (max " + MAX_DEVICE_INFO_LENGTH + " characters)"
                );
            }
        }

        // IP address validation
        if (StringUtils.hasText(sessionDTO.getIpAddress())) {
            if (!isValidIpAddress(sessionDTO.getIpAddress())) {
                throw new IllegalArgumentException("Invalid IP address format");
            }
        }
    }

    public void validateAccess(String livestreamId, String sessionToken) {
        if (!StringUtils.hasText(livestreamId)) {
            throw new IllegalArgumentException("Livestream ID is required");
        }

        if (!StringUtils.hasText(sessionToken)) {
            throw new IllegalArgumentException("Session token is required");
        }
    }

    public void validateLivestreamAccess(Livestream livestream, ViewerSession session) {
        if (livestream == null) {
            throw new IllegalArgumentException("Livestream not found");
        }

        if (session == null) {
            throw new IllegalArgumentException("Invalid session");
        }

        // Check if livestream is active
        if (!livestream.isLive()) {
            throw new IllegalStateException("Livestream is not currently active");
        }

        // Check if session is active
        if (!session.isActive()) {
            throw new IllegalStateException("Session has ended");
        }

        // Check if session has exceeded maximum duration
        if (session.getStartedAt() != null) {
            java.time.Duration duration = java.time.Duration.between(
                    session.getStartedAt(), java.time.LocalDateTime.now()
            );
            if (duration.toHours() >= MAX_SESSION_DURATION_HOURS) {
                throw new IllegalStateException(
                        "Session has exceeded maximum duration of " +
                                MAX_SESSION_DURATION_HOURS + " hours"
                );
            }
        }
    }

    public void validateSessionEnd(ViewerSession session) {
        if (session == null) {
            throw new IllegalArgumentException("Session not found");
        }

        if (!session.isActive()) {
            throw new IllegalStateException("Session has already ended");
        }
    }

    public void validateDeviceInfo(String deviceInfo) {
        if (deviceInfo != null && deviceInfo.length() > MAX_DEVICE_INFO_LENGTH) {
            throw new IllegalArgumentException(
                    "Device info too long (max " + MAX_DEVICE_INFO_LENGTH + " characters)"
            );
        }
    }

    private boolean isValidIpAddress(String ip) {
        if (ip == null || ip.isEmpty()) return false;

        // Check IPv4
        if (IPV4_PATTERN.matcher(ip).matches()) {
            return true;
        }

        // Basic IPv6 check (simplified)
        if (ip.contains(":")) {
            String[] parts = ip.split(":");
            if (parts.length >= 3 && parts.length <= 8) {
                return true;
            }
        }

        return false;
    }

    public void validateRateLimiting(String sessionToken, int currentRequests, int maxRequests) {
        if (currentRequests >= maxRequests) {
            throw new IllegalStateException("Rate limit exceeded. Please try again later.");
        }
    }
}