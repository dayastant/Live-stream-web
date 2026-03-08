package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoDTO {
    private String id;
    private String title;
    private String videoUrl;
    private Integer duration;
    private LocalDateTime uploadedAt;
    private Long fileSize;
    private String thumbnailUrl;
    private String videoFormat;
    private String resolution;
    private String livestreamId;
    private String livestreamTitle;

    // Private constructor for builder
    private VideoDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.videoUrl = builder.videoUrl;
        this.duration = builder.duration;
        this.uploadedAt = builder.uploadedAt;
        this.fileSize = builder.fileSize;
        this.thumbnailUrl = builder.thumbnailUrl;
        this.videoFormat = builder.videoFormat;
        this.resolution = builder.resolution;
        this.livestreamId = builder.livestreamId;
        this.livestreamTitle = builder.livestreamTitle;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String id;
        private String title;
        private String videoUrl;
        private Integer duration;
        private LocalDateTime uploadedAt;
        private Long fileSize;
        private String thumbnailUrl;
        private String videoFormat;
        private String resolution;
        private String livestreamId;
        private String livestreamTitle;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder videoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
            return this;
        }

        public Builder duration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public Builder uploadedAt(LocalDateTime uploadedAt) {
            this.uploadedAt = uploadedAt;
            return this;
        }

        public Builder fileSize(Long fileSize) {
            this.fileSize = fileSize;
            return this;
        }

        public Builder thumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
            return this;
        }

        public Builder videoFormat(String videoFormat) {
            this.videoFormat = videoFormat;
            return this;
        }

        public Builder resolution(String resolution) {
            this.resolution = resolution;
            return this;
        }

        public Builder livestreamId(String livestreamId) {
            this.livestreamId = livestreamId;
            return this;
        }

        public Builder livestreamTitle(String livestreamTitle) {
            this.livestreamTitle = livestreamTitle;
            return this;
        }

        public VideoDTO build() {
            return new VideoDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public String getResolution() {
        return resolution;
    }

    public String getLivestreamId() {
        return livestreamId;
    }

    public String getLivestreamTitle() {
        return livestreamTitle;
    }

    // ===== Computed fields =====

    public String getFormattedDuration() {
        if (duration == null) return "00:00";
        int hours = duration / 3600;
        int minutes = (duration % 3600) / 60;
        int seconds = duration % 60;

        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

    public String getFileSizeInMB() {
        if (fileSize == null) return "0 MB";
        double mb = fileSize / (1024.0 * 1024.0);
        return String.format("%.2f MB", mb);
    }

    public String getUploadedAtFormatted() {
        return uploadedAt != null ?
                uploadedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}