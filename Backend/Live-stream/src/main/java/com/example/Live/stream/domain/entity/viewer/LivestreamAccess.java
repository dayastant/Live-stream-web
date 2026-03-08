package com.example.Live.stream.domain.entity.viewer;

import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.entity.livestream.Livestream;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "livestream_accesses")
public class LivestreamAccess extends BaseEntity {

    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_session_id", nullable = false)
    private ViewerSession viewerSession;

    // Default constructor
    public LivestreamAccess() {
        this.joinedAt = LocalDateTime.now();
    }

    // Constructor with fields
    public LivestreamAccess(Livestream livestream, ViewerSession viewerSession) {
        this.livestream = livestream;
        this.viewerSession = viewerSession;
        this.joinedAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    @Override
    public String getId() {
        return super.getId();
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(LocalDateTime joinedAt) {
        this.joinedAt = joinedAt;
    }

    public LocalDateTime getLeftAt() {
        return leftAt;
    }

    public void setLeftAt(LocalDateTime leftAt) {
        this.leftAt = leftAt;
    }

    public Livestream getLivestream() {
        return livestream;
    }

    public void setLivestream(Livestream livestream) {
        this.livestream = livestream;
    }

    public ViewerSession getViewerSession() {
        return viewerSession;
    }

    public void setViewerSession(ViewerSession viewerSession) {
        this.viewerSession = viewerSession;
    }

    // ===== Business methods =====

    public void leave() {
        this.leftAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return leftAt == null;
    }

    public long getWatchDurationInSeconds() {
        if (joinedAt == null) return 0;
        LocalDateTime end = leftAt != null ? leftAt : LocalDateTime.now();
        return java.time.Duration.between(joinedAt, end).getSeconds();
    }
}