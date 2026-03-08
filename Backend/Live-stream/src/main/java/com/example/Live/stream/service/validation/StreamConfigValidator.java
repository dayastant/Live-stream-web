package com.example.Live.stream.service.validation;

import com.example.Live.stream.service.dto.StreamConfigDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class StreamConfigValidator {

    private static final int MIN_BITRATE = 500; // kbps
    private static final int MAX_BITRATE = 10000; // kbps
    private static final String[] VALID_RESOLUTIONS = {"480p", "720p", "1080p", "1440p", "4K"};

    public void validateForCreation(StreamConfigDTO configDTO) {
        if (configDTO == null) {
            throw new IllegalArgumentException("Stream config data cannot be null");
        }

        // Validate RTMP URL
        if (!StringUtils.hasText(configDTO.getRtmpUrl())) {
            throw new IllegalArgumentException("RTMP URL is required");
        }

        if (!configDTO.getRtmpUrl().startsWith("rtmp://")) {
            throw new IllegalArgumentException("RTMP URL must start with rtmp://");
        }

        // Validate stream key
        if (!StringUtils.hasText(configDTO.getStreamKey())) {
            throw new IllegalArgumentException("Stream key is required");
        }

        if (configDTO.getStreamKey().length() < 8 || configDTO.getStreamKey().length() > 64) {
            throw new IllegalArgumentException("Stream key must be between 8 and 64 characters");
        }

        // Validate HLS URL if provided
        if (StringUtils.hasText(configDTO.getHlsUrl())) {
            if (!configDTO.getHlsUrl().startsWith("http://") &&
                    !configDTO.getHlsUrl().startsWith("https://")) {
                throw new IllegalArgumentException("HLS URL must start with http:// or https://");
            }
        }

        // Validate bitrate
        if (configDTO.getBitrate() != null) {
            if (configDTO.getBitrate() < MIN_BITRATE || configDTO.getBitrate() > MAX_BITRATE) {
                throw new IllegalArgumentException(
                        "Bitrate must be between " + MIN_BITRATE + " and " + MAX_BITRATE + " kbps"
                );
            }
        }

        // Validate resolution
        if (StringUtils.hasText(configDTO.getResolution())) {
            boolean validResolution = false;
            for (String res : VALID_RESOLUTIONS) {
                if (res.equals(configDTO.getResolution())) {
                    validResolution = true;
                    break;
                }
            }
            if (!validResolution) {
                throw new IllegalArgumentException(
                        "Invalid resolution. Valid values: " + String.join(", ", VALID_RESOLUTIONS)
                );
            }
        }
    }

    public void validateStreamKeyUniqueness(boolean exists) {
        if (exists) {
            throw new IllegalArgumentException("Stream key already exists");
        }
    }

    public void validateForUpdate(StreamConfigDTO dto, StreamConfigDTO existing) {
        if (dto == null) return;

        // Stream key cannot be changed
        if (dto.getStreamKey() != null && !dto.getStreamKey().equals(existing.getStreamKey())) {
            throw new IllegalArgumentException("Stream key cannot be changed");
        }

        // RTMP URL cannot be changed
        if (dto.getRtmpUrl() != null && !dto.getRtmpUrl().equals(existing.getRtmpUrl())) {
            throw new IllegalArgumentException("RTMP URL cannot be changed");
        }
    }
}