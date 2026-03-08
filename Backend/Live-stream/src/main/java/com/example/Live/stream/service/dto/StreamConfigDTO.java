package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StreamConfigDTO {
    private String id;
    private String rtmpUrl;
    private String streamKey;
    private String hlsUrl;
    private Integer bitrate;
    private String resolution;
    private LocalDateTime createdAt;
    private String livestreamId;
    private String livestreamTitle;

    // Private constructor for builder
    private StreamConfigDTO(Builder builder) {
        this.id = builder.id;
        this.rtmpUrl = builder.rtmpUrl;
        this.streamKey = builder.streamKey;
        this.hlsUrl = builder.hlsUrl;
        this.bitrate = builder.bitrate;
        this.resolution = builder.resolution;
        this.createdAt = builder.createdAt;
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
        private String rtmpUrl;
        private String streamKey;
        private String hlsUrl;
        private Integer bitrate;
        private String resolution;
        private LocalDateTime createdAt;
        private String livestreamId;
        private String livestreamTitle;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder rtmpUrl(String rtmpUrl) {
            this.rtmpUrl = rtmpUrl;
            return this;
        }

        public Builder streamKey(String streamKey) {
            this.streamKey = streamKey;
            return this;
        }

        public Builder hlsUrl(String hlsUrl) {
            this.hlsUrl = hlsUrl;
            return this;
        }

        public Builder bitrate(Integer bitrate) {
            this.bitrate = bitrate;
            return this;
        }

        public Builder resolution(String resolution) {
            this.resolution = resolution;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
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

        public StreamConfigDTO build() {
            return new StreamConfigDTO(this);
        }
    }

    // ===== Getters =====

    public String getId() {
        return id;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public String getResolution() {
        return resolution;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getLivestreamId() {
        return livestreamId;
    }

    public String getLivestreamTitle() {
        return livestreamTitle;
    }

    // ===== Computed fields =====

    public String getFullRtmpUrl() {
        return rtmpUrl + "/" + streamKey;
    }

    public String getBitrateDisplay() {
        return bitrate != null ? bitrate + " kbps" : null;
    }
}