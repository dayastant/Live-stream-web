package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.livestream.Livestream;
import com.example.Live.stream.domain.enums.LivestreamStatus;
import com.example.Live.stream.service.dto.LivestreamDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class LivestreamValidator {

    private static final int MAX_TITLE_LENGTH = 200;
    private static final int MAX_DESCRIPTION_LENGTH = 1000;
    private static final int MAX_VIEWERS_LIMIT = 10000;
    private static final int MIN_SCHEDULE_HOURS = 1;

    public void validateForCreation(LivestreamDTO livestreamDTO) {
        if (livestreamDTO == null) {
            throw new IllegalArgumentException("Livestream data cannot be null");
        }

        // Validate title
        if (!StringUtils.hasText(livestreamDTO.getTitle())) {
            throw new IllegalArgumentException("Title is required");
        }

        if (livestreamDTO.getTitle().length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException(
                    "Title cannot exceed " + MAX_TITLE_LENGTH + " characters"
            );
        }

        // Validate description
        if (livestreamDTO.getDescription() != null &&
                livestreamDTO.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException(
                    "Description cannot exceed " + MAX_DESCRIPTION_LENGTH + " characters"
            );
        }

        // Validate scheduled time
        if (livestreamDTO.getScheduledAt() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (livestreamDTO.getScheduledAt().isBefore(now)) {
                throw new IllegalArgumentException("Scheduled time cannot be in the past");
            }

            long hoursUntil = ChronoUnit.HOURS.between(now, livestreamDTO.getScheduledAt());
            if (hoursUntil < MIN_SCHEDULE_HOURS) {
                throw new IllegalArgumentException(
                        "Livestream must be scheduled at least " + MIN_SCHEDULE_HOURS +
                                " hour in advance"
                );
            }
        }

        // Validate max viewers
        if (livestreamDTO.getMaxViewers() != null) {
            if (livestreamDTO.getMaxViewers() < 0) {
                throw new IllegalArgumentException("Max viewers cannot be negative");
            }

            if (livestreamDTO.getMaxViewers() > MAX_VIEWERS_LIMIT) {
                throw new IllegalArgumentException(
                        "Max viewers cannot exceed " + MAX_VIEWERS_LIMIT
                );
            }
        }
    }

    public void validateForUpdate(LivestreamDTO livestreamDTO, Livestream existing) {
        if (livestreamDTO == null) return;

        // Cannot update certain fields based on status
        if (existing.getStatus() == LivestreamStatus.LIVE) {
            if (livestreamDTO.getTitle() != null &&
                    !livestreamDTO.getTitle().equals(existing.getTitle())) {
                throw new IllegalStateException("Cannot change title while stream is live");
            }

            if (livestreamDTO.getScheduledAt() != null) {
                throw new IllegalStateException("Cannot change scheduled time while stream is live");
            }

            if (livestreamDTO.getMaxViewers() != null &&
                    !livestreamDTO.getMaxViewers().equals(existing.getMaxViewers())) {
                throw new IllegalStateException("Cannot change max viewers while stream is live");
            }
        }

        if (existing.getStatus() == LivestreamStatus.ENDED) {
            throw new IllegalStateException("Cannot update an ended livestream");
        }

        // Validate scheduled time if being updated
        if (livestreamDTO.getScheduledAt() != null) {
            LocalDateTime now = LocalDateTime.now();
            if (livestreamDTO.getScheduledAt().isBefore(now)) {
                throw new IllegalArgumentException("Scheduled time cannot be in the past");
            }
        }
    }

    public void validateCanStart(Livestream livestream) {
        if (livestream == null) {
            throw new IllegalArgumentException("Livestream cannot be null");
        }

        if (livestream.getStatus() != LivestreamStatus.SCHEDULED) {
            throw new IllegalStateException(
                    "Cannot start livestream with status: " + livestream.getStatus()
            );
        }

        // Check if scheduled time has been reached
        if (livestream.getScheduledAt() != null &&
                livestream.getScheduledAt().isAfter(LocalDateTime.now())) {
            throw new IllegalStateException(
                    "Cannot start livestream before scheduled time: " + livestream.getScheduledAt()
            );
        }

        // Check if stream config exists
        if (livestream.getStreamConfigs() == null ||
                livestream.getStreamConfigs().isEmpty()) {
            throw new IllegalStateException("Stream configuration is required before starting");
        }

        // Check if admin exists
        if (livestream.getAdmin() == null) {
            throw new IllegalStateException("Livestream must have an assigned admin");
        }
    }

    public void validateCanEnd(Livestream livestream) {
        if (livestream == null) {
            throw new IllegalArgumentException("Livestream cannot be null");
        }

        if (livestream.getStatus() != LivestreamStatus.LIVE) {
            throw new IllegalStateException(
                    "Cannot end livestream with status: " + livestream.getStatus()
            );
        }

        // Check minimum stream duration (optional)
        if (livestream.getStartedAt() != null) {
            long minutesLive = ChronoUnit.MINUTES.between(
                    livestream.getStartedAt(), LocalDateTime.now()
            );
            if (minutesLive < 1) {
                throw new IllegalStateException("Stream must be live for at least 1 minute");
            }
        }
    }

    public void validateStreamKey(String streamKey) {
        if (!StringUtils.hasText(streamKey)) {
            throw new IllegalArgumentException("Stream key is required");
        }

        if (streamKey.length() < 10 || streamKey.length() > 100) {
            throw new IllegalArgumentException("Stream key must be between 10 and 100 characters");
        }

        // Stream key should be alphanumeric with some special chars
        if (!streamKey.matches("^[a-zA-Z0-9_-]+$")) {
            throw new IllegalArgumentException(
                    "Stream key can only contain letters, numbers, underscores and hyphens"
            );
        }
    }

    public void validateRTMPUrl(String rtmpUrl) {
        if (!StringUtils.hasText(rtmpUrl)) {
            throw new IllegalArgumentException("RTMP URL is required");
        }

        if (!rtmpUrl.startsWith("rtmp://")) {
            throw new IllegalArgumentException("RTMP URL must start with rtmp://");
        }
    }

    public void validateViewerLimit(Integer currentViewers, Integer maxViewers) {
        if (maxViewers != null && currentViewers >= maxViewers) {
            throw new IllegalStateException("Maximum viewer limit reached");
        }
    }
}