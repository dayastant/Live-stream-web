package com.example.Live.stream.domain.entity.livestream;


import com.example.Live.stream.domain.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "videos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "livestream")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Video extends BaseEntity {

    @Column(length = 200)
    private String title;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    private Integer duration; // duration in seconds

    @Column(name = "uploaded_at")
    @Builder.Default
    private java.time.LocalDateTime uploadedAt = java.time.LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id")
    private Livestream livestream;

    @Column(name = "file_size")
    private Long fileSize; // in bytes

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "video_format")
    private String videoFormat; // MP4, MKV, etc.

    @Column(name = "resolution")
    private String resolution; // 1080p, 720p, etc.

    // Business methods
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