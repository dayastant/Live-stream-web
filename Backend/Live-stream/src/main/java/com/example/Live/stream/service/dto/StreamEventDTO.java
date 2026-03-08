package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.StreamEventType;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamEventDTO {
    private String id;
    private StreamEventType eventType;
    private LocalDateTime eventTime;
    private String livestreamId;
    private String livestreamTitle;

    // Private constructor for builder
    private StreamEventDTO(Builder builder) {
        this.id = builder.id;
        this.eventType = builder.eventType;
        this.eventTime = builder.eventTime;
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
        private StreamEventType eventType;
        private LocalDateTime eventTime;
        private String livestreamId;
        private String livestreamTitle;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder eventType(StreamEventType eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder eventTime(LocalDateTime eventTime) {
            this.eventTime = eventTime;
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

        public StreamEventDTO build() {
            return new StreamEventDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public StreamEventType getEventType() {
        return eventType;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public String getLivestreamId() {
        return livestreamId;
    }

    public String getLivestreamTitle() {
        return livestreamTitle;
    }

    // ===== Computed fields =====

    public String getFormattedEventTime() {
        return eventTime != null ?
                eventTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getEventTypeDisplay() {
        return eventType != null ? eventType.name() : null;
    }
}