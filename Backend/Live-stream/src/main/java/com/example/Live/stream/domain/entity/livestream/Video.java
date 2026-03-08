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

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "video_format")
    private String videoFormat;

    @Column(name = "resolution")
    private String resolution;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id")
    private Livestream livestream;

    // Default constructor
    public Video() {
        this.uploadedAt = LocalDateTime.now();
    }

    // Constructor with fields
    public Video(String title, String videoUrl, Integer duration,
                 Long fileSize, String thumbnailUrl, String videoFormat,
                 String resolution, Livestream livestream) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.duration = duration;
        this.fileSize = fileSize;
        this.thumbnailUrl = thumbnailUrl;
        this.videoFormat = videoFormat;
        this.resolution = resolution;
        this.livestream = livestream;
        this.uploadedAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    @Override
    public String getId() {
        return super.getId();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getVideoFormat() {
        return videoFormat;
    }

    public void setVideoFormat(String videoFormat) {
        this.videoFormat = videoFormat;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public Livestream getLivestream() {
        return livestream;
    }

    public void setLivestream(Livestream livestream) {
        this.livestream = livestream;
    }

    // ===== Business methods =====

    public void associateWithLivestream(Livestream livestream) {
        this.livestream = livestream;
    }

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
}