package com.example.Live.stream.service.validation;

import com.example.Live.stream.domain.entity.livestream.Video;
import com.example.Live.stream.service.dto.VideoDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class VideoValidator {

    private static final long MAX_FILE_SIZE = 1024L * 1024L * 1024L; // 1GB
    private static final int MAX_DURATION = 7200; // 2 hours in seconds
    private static final int MIN_DURATION = 1; // 1 second
    private static final String[] ALLOWED_FORMATS = {"MP4", "MKV", "AVI", "MOV", "WEBM"};
    private static final String[] ALLOWED_RESOLUTIONS = {"480p", "720p", "1080p", "1440p", "4K"};

    public void validateForUpload(VideoDTO videoDTO) {
        if (videoDTO == null) {
            throw new IllegalArgumentException("Video data cannot be null");
        }

        // Validate video URL
        if (!StringUtils.hasText(videoDTO.getVideoUrl())) {
            throw new IllegalArgumentException("Video URL is required");
        }

        if (!videoDTO.getVideoUrl().startsWith("http://") &&
                !videoDTO.getVideoUrl().startsWith("https://") &&
                !videoDTO.getVideoUrl().startsWith("/videos/")) {
            throw new IllegalArgumentException("Invalid video URL format");
        }

        // Validate file size
        if (videoDTO.getFileSize() != null) {
            if (videoDTO.getFileSize() <= 0) {
                throw new IllegalArgumentException("File size must be positive");
            }
            if (videoDTO.getFileSize() > MAX_FILE_SIZE) {
                throw new IllegalArgumentException(
                        "File size exceeds maximum allowed (1GB)"
                );
            }
        }

        // Validate duration
        if (videoDTO.getDuration() != null) {
            if (videoDTO.getDuration() < MIN_DURATION) {
                throw new IllegalArgumentException("Duration must be at least 1 second");
            }
            if (videoDTO.getDuration() > MAX_DURATION) {
                throw new IllegalArgumentException(
                        "Video duration exceeds maximum allowed (2 hours)"
                );
            }
        }

        // Validate video format
        if (StringUtils.hasText(videoDTO.getVideoFormat())) {
            boolean validFormat = false;
            for (String format : ALLOWED_FORMATS) {
                if (format.equalsIgnoreCase(videoDTO.getVideoFormat())) {
                    validFormat = true;
                    break;
                }
            }
            if (!validFormat) {
                throw new IllegalArgumentException(
                        "Invalid video format. Allowed formats: " + String.join(", ", ALLOWED_FORMATS)
                );
            }
        }

        // Validate resolution
        if (StringUtils.hasText(videoDTO.getResolution())) {
            boolean validResolution = false;
            for (String res : ALLOWED_RESOLUTIONS) {
                if (res.equals(videoDTO.getResolution())) {
                    validResolution = true;
                    break;
                }
            }
            if (!validResolution) {
                throw new IllegalArgumentException(
                        "Invalid resolution. Valid values: " + String.join(", ", ALLOWED_RESOLUTIONS)
                );
            }
        }

        // Validate title length
        if (videoDTO.getTitle() != null && videoDTO.getTitle().length() > 200) {
            throw new IllegalArgumentException("Title cannot exceed 200 characters");
        }
    }

    public void validateForDeletion(Video video) {
        if (video == null) {
            throw new IllegalArgumentException("Video not found");
        }

        // Don't allow deletion of videos associated with active livestreams
        if (video.getLivestream() != null && video.getLivestream().isLive()) {
            throw new IllegalStateException(
                    "Cannot delete video while associated livestream is active"
            );
        }
    }

    public void validateVideoUrlUniqueness(boolean exists) {
        if (exists) {
            throw new IllegalArgumentException("Video URL already exists");
        }
    }

    public void validateForUpdate(VideoDTO dto, Video existing) {
        if (dto == null) return;

        // Video URL cannot be changed
        if (dto.getVideoUrl() != null && !dto.getVideoUrl().equals(existing.getVideoUrl())) {
            throw new IllegalArgumentException("Video URL cannot be changed");
        }
    }
}