package com.example.Live.stream.domain.entity.livestream;

import com.example.Live.stream.domain.base.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "videos")
public class Video extends BaseEntity {

    @Column(length = 200)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    private Integer duration;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id")
    private Livestream livestream;

    // Default constructor
    public Video() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Constructor with fields
    public Video(String title, String videoUrl, Integer duration, Livestream livestream) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.duration = duration;
        this.livestream = livestream;
        this.uploadedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getVideoUrl() { return videoUrl; }
    public void setVideoUrl(String videoUrl) { this.videoUrl = videoUrl; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public Livestream getLivestream() { return livestream; }
    public void setLivestream(Livestream livestream) { this.livestream = livestream; }
}