package com.example.Live.stream.service.validation;

import com.example.Live.stream.service.dto.AnalyticsDTO;
import org.springframework.stereotype.Component;

@Component
public class AnalyticsValidator {

    private static final int MAX_VIEWERS = 1000000;

    public void validateForCreation(AnalyticsDTO analyticsDTO) {
        if (analyticsDTO == null) {
            throw new IllegalArgumentException("Analytics data cannot be null");
        }

        // Validate concurrent viewers
        if (analyticsDTO.getConcurrentViewers() == null) {
            throw new IllegalArgumentException("Concurrent viewers is required");
        }

        if (analyticsDTO.getConcurrentViewers() < 0) {
            throw new IllegalArgumentException("Concurrent viewers cannot be negative");
        }

        if (analyticsDTO.getConcurrentViewers() > MAX_VIEWERS) {
            throw new IllegalArgumentException(
                    "Concurrent viewers cannot exceed " + MAX_VIEWERS
            );
        }

        // Validate peak viewers
        if (analyticsDTO.getPeakViewers() != null) {
            if (analyticsDTO.getPeakViewers() < 0) {
                throw new IllegalArgumentException("Peak viewers cannot be negative");
            }

            if (analyticsDTO.getPeakViewers() < analyticsDTO.getConcurrentViewers()) {
                throw new IllegalArgumentException(
                        "Peak viewers cannot be less than concurrent viewers"
                );
            }

            if (analyticsDTO.getPeakViewers() > MAX_VIEWERS) {
                throw new IllegalArgumentException(
                        "Peak viewers cannot exceed " + MAX_VIEWERS
                );
            }
        }
    }

    public void validateDateRange(java.time.LocalDateTime start, java.time.LocalDateTime end) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Start and end dates are required");
        }

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }

        if (end.isAfter(java.time.LocalDateTime.now())) {
            throw new IllegalArgumentException("End date cannot be in the future");
        }

        // Maximum range of 30 days
        if (java.time.Duration.between(start, end).toDays() > 30) {
            throw new IllegalArgumentException("Date range cannot exceed 30 days");
        }
    }
}