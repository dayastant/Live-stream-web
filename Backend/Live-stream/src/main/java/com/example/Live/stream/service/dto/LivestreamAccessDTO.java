package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivestreamAccessDTO {
    private String id;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;
    private String livestreamId;
    private String livestreamTitle;
    private String viewerSessionId;

    // Private constructor for builder
    private LivestreamAccessDTO(Builder builder) {
        this.id = builder.id;
        this.joinedAt = builder.joinedAt;
        this.leftAt = builder.leftAt;
        this.livestreamId = builder.livestreamId;
        this.livestreamTitle = builder.livestreamTitle;
        this.viewerSessionId = builder.viewerSessionId;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String id;
        private LocalDateTime joinedAt;
        private LocalDateTime leftAt;
        private String livestreamId;
        private String livestreamTitle;
        private String viewerSessionId;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder joinedAt(LocalDateTime joinedAt) {
            this.joinedAt = joinedAt;
            return this;
        }

        public Builder leftAt(LocalDateTime leftAt) {
            this.leftAt = leftAt;
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

        public Builder viewerSessionId(String viewerSessionId) {
            this.viewerSessionId = viewerSessionId;
            return this;
        }

        public LivestreamAccessDTO build() {
            return new LivestreamAccessDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public String getLivestreamId() {
        return livestreamId;
    }

    public String getLivestreamTitle() {
        return livestreamTitle;
    }

    public String getViewerSessionId() {
        return viewerSessionId;
    }

    // ===== Computed fields =====

    public String getJoinedAtFormatted() {
        return joinedAt != null ?
                joinedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getLeftAtFormatted() {
        return leftAt != null ?
                leftAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public boolean isActive() {
        return leftAt == null;
    }

    public String getWatchDuration() {
        if (joinedAt != null) {
            LocalDateTime end = leftAt != null ? leftAt : LocalDateTime.now();
            long minutes = java.time.Duration.between(joinedAt, end).toMinutes();
            return minutes + " minutes";
        }
        return null;
    }
}