package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.LivestreamStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivestreamDTO {
    private String id;
    private String title;
    private String description;
    private LivestreamStatus status;
    private LocalDateTime scheduledAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer maxViewers;
    private Integer currentViewers;
    private String adminId;
    private String adminUsername;
    private List<StreamConfigDTO> streamConfigs;
    private List<StreamEventDTO> streamEvents;
    private List<VideoDTO> videos;
    private List<AnalyticsDTO> analytics;

    // Private constructor for builder
    private LivestreamDTO(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.status = builder.status;
        this.scheduledAt = builder.scheduledAt;
        this.startedAt = builder.startedAt;
        this.endedAt = builder.endedAt;
        this.maxViewers = builder.maxViewers;
        this.currentViewers = builder.currentViewers;
        this.adminId = builder.adminId;
        this.adminUsername = builder.adminUsername;
        this.streamConfigs = builder.streamConfigs;
        this.streamEvents = builder.streamEvents;
        this.videos = builder.videos;
        this.analytics = builder.analytics;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String id;
        private String title;
        private String description;
        private LivestreamStatus status;
        private LocalDateTime scheduledAt;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private Integer maxViewers;
        private Integer currentViewers;
        private String adminId;
        private String adminUsername;
        private List<StreamConfigDTO> streamConfigs;
        private List<StreamEventDTO> streamEvents;
        private List<VideoDTO> videos;
        private List<AnalyticsDTO> analytics;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder status(LivestreamStatus status) {
            this.status = status;
            return this;
        }

        public Builder scheduledAt(LocalDateTime scheduledAt) {
            this.scheduledAt = scheduledAt;
            return this;
        }

        public Builder startedAt(LocalDateTime startedAt) {
            this.startedAt = startedAt;
            return this;
        }

        public Builder endedAt(LocalDateTime endedAt) {
            this.endedAt = endedAt;
            return this;
        }

        public Builder maxViewers(Integer maxViewers) {
            this.maxViewers = maxViewers;
            return this;
        }

        public Builder currentViewers(Integer currentViewers) {
            this.currentViewers = currentViewers;
            return this;
        }

        public Builder adminId(String adminId) {
            this.adminId = adminId;
            return this;
        }

        public Builder adminUsername(String adminUsername) {
            this.adminUsername = adminUsername;
            return this;
        }

        public Builder streamConfigs(List<StreamConfigDTO> streamConfigs) {
            this.streamConfigs = streamConfigs;
            return this;
        }

        public Builder streamEvents(List<StreamEventDTO> streamEvents) {
            this.streamEvents = streamEvents;
            return this;
        }

        public Builder videos(List<VideoDTO> videos) {
            this.videos = videos;
            return this;
        }

        public Builder analytics(List<AnalyticsDTO> analytics) {
            this.analytics = analytics;
            return this;
        }

        public LivestreamDTO build() {
            return new LivestreamDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LivestreamStatus getStatus() {
        return status;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public Integer getMaxViewers() {
        return maxViewers;
    }

    public Integer getCurrentViewers() {
        return currentViewers;
    }

    public String getAdminId() {
        return adminId;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public List<StreamConfigDTO> getStreamConfigs() {
        return streamConfigs;
    }

    public List<StreamEventDTO> getStreamEvents() {
        return streamEvents;
    }

    public List<VideoDTO> getVideos() {
        return videos;
    }

    public List<AnalyticsDTO> getAnalytics() {
        return analytics;
    }

    // ===== Computed fields =====

    public String getStatusDisplay() {
        return status != null ? status.name() : null;
    }

    public String getDuration() {
        if (startedAt != null && endedAt != null) {
            long minutes = java.time.Duration.between(startedAt, endedAt).toMinutes();
            return minutes + " minutes";
        }
        return null;
    }

    public boolean isLive() {
        return LivestreamStatus.LIVE.equals(status);
    }

    public String getScheduledAtFormatted() {
        return scheduledAt != null ?
                scheduledAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}