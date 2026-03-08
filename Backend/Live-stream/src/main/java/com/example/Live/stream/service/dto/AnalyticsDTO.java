package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnalyticsDTO {
    private String id;
    private Integer concurrentViewers;
    private Integer peakViewers;
    private LocalDateTime recordedAt;
    private String livestreamId;
    private String livestreamTitle;

    // Private constructor for builder
    private AnalyticsDTO(Builder builder) {
        this.id = builder.id;
        this.concurrentViewers = builder.concurrentViewers;
        this.peakViewers = builder.peakViewers;
        this.recordedAt = builder.recordedAt;
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
        private Integer concurrentViewers;
        private Integer peakViewers;
        private LocalDateTime recordedAt;
        private String livestreamId;
        private String livestreamTitle;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder concurrentViewers(Integer concurrentViewers) {
            this.concurrentViewers = concurrentViewers;
            return this;
        }

        public Builder peakViewers(Integer peakViewers) {
            this.peakViewers = peakViewers;
            return this;
        }

        public Builder recordedAt(LocalDateTime recordedAt) {
            this.recordedAt = recordedAt;
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

        public AnalyticsDTO build() {
            return new AnalyticsDTO(this);
        }
    }

    // Getters
    public String getId() { return id; }
    public Integer getConcurrentViewers() { return concurrentViewers; }
    public Integer getPeakViewers() { return peakViewers; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public String getLivestreamId() { return livestreamId; }
    public String getLivestreamTitle() { return livestreamTitle; }

    // Computed fields
    public String getRecordedAtFormatted() {
        return recordedAt != null ?
                recordedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}