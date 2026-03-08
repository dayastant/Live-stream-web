package com.example.Live.stream.domain.entity.livestream;

import com.example.Live.stream.domain.base.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stream_configs")
public class StreamConfig extends BaseEntity {

    @Column(name = "rtmp_url", nullable = false)
    private String rtmpUrl;

    @Column(name = "stream_key", nullable = false)
    private String streamKey;

    @Column(name = "hls_url")
    private String hlsUrl;

    private Integer bitrate;

    private String resolution;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    // Default constructor
    public StreamConfig() {
        this.createdAt = LocalDateTime.now();
    }

    // Constructor with fields
    public StreamConfig(String rtmpUrl, String streamKey, String hlsUrl,
                        Integer bitrate, String resolution, Livestream livestream) {
        this.rtmpUrl = rtmpUrl;
        this.streamKey = streamKey;
        this.hlsUrl = hlsUrl;
        this.bitrate = bitrate;
        this.resolution = resolution;
        this.livestream = livestream;
        this.createdAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    @Override
    public String getId() {
        return super.getId();
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

    public String getHlsUrl() {
        return hlsUrl;
    }

    public void setHlsUrl(String hlsUrl) {
        this.hlsUrl = hlsUrl;
    }

    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Livestream getLivestream() {
        return livestream;
    }

    public void setLivestream(Livestream livestream) {
        this.livestream = livestream;
    }

    // ===== Business methods =====

    public String getFullRtmpUrl() {
        return rtmpUrl + "/" + streamKey;
    }
}