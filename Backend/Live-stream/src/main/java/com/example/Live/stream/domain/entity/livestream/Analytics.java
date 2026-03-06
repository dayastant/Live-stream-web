package com.example.Live.stream.domain.entity.livestream;

import com.example.Live.stream.domain.base.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
public class Analytics extends BaseEntity {

    @Column(name = "concurrent_viewers", nullable = false)
    private Integer concurrentViewers;

    @Column(name = "peak_viewers")
    private Integer peakViewers;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    // Default constructor
    public Analytics() {
        this.recordedAt = LocalDateTime.now();
    }

    // Constructor with fields
    public Analytics(Integer concurrentViewers, Integer peakViewers, Livestream livestream) {
        this.concurrentViewers = concurrentViewers;
        this.peakViewers = peakViewers;
        this.livestream = livestream;
        this.recordedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Integer getConcurrentViewers() { return concurrentViewers; }
    public void setConcurrentViewers(Integer concurrentViewers) { this.concurrentViewers = concurrentViewers; }

    public Integer getPeakViewers() { return peakViewers; }
    public void setPeakViewers(Integer peakViewers) { this.peakViewers = peakViewers; }

    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }

    public Livestream getLivestream() { return livestream; }
    public void setLivestream(Livestream livestream) { this.livestream = livestream; }
}