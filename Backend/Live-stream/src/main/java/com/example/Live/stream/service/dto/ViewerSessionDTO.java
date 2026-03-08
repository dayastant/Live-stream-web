package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewerSessionDTO {
    private String id;
    private String sessionToken;
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private List<LivestreamAccessDTO> accesses;

    // Private constructor for builder
    private ViewerSessionDTO(Builder builder) {
        this.id = builder.id;
        this.sessionToken = builder.sessionToken;
        this.ipAddress = builder.ipAddress;
        this.deviceInfo = builder.deviceInfo;
        this.startedAt = builder.startedAt;
        this.endedAt = builder.endedAt;
        this.accesses = builder.accesses;
    }

    // Static builder method
    public static Builder builder() {
        return new Builder();
    }

    // Builder class
    public static class Builder {
        private String id;
        private String sessionToken;
        private String ipAddress;
        private String deviceInfo;
        private LocalDateTime startedAt;
        private LocalDateTime endedAt;
        private List<LivestreamAccessDTO> accesses;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder sessionToken(String sessionToken) {
            this.sessionToken = sessionToken;
            return this;
        }

        public Builder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder deviceInfo(String deviceInfo) {
            this.deviceInfo = deviceInfo;
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

        public Builder accesses(List<LivestreamAccessDTO> accesses) {
            this.accesses = accesses;
            return this;
        }

        public ViewerSessionDTO build() {
            return new ViewerSessionDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public List<LivestreamAccessDTO> getAccesses() {
        return accesses;
    }

    // ===== Computed fields =====

    public String getStartedAtFormatted() {
        return startedAt != null ?
                startedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getEndedAtFormatted() {
        return endedAt != null ?
                endedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public boolean isActive() {
        return endedAt == null;
    }

    public String getDuration() {
        if (startedAt != null) {
            LocalDateTime end = endedAt != null ? endedAt : LocalDateTime.now();
            long minutes = java.time.Duration.between(startedAt, end).toMinutes();
            return minutes + " minutes";
        }
        return null;
    }
}