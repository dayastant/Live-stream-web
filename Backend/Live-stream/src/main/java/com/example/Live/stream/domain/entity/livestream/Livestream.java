package com.example.Live.stream.domain.entity.livestream;

import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.domain.entity.viewer.LivestreamAccess;
import com.example.Live.stream.domain.enums.LivestreamStatus;
import com.example.Live.stream.domain.enums.StreamEventType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livestreams")
public class Livestream extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LivestreamStatus status = LivestreamStatus.SCHEDULED;

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @Column(name = "max_viewers")
    private Integer maxViewers;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Admin admin;

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StreamConfig> streamConfigs = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<StreamEvent> streamEvents = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Analytics> analytics = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<LivestreamAccess> accesses = new ArrayList<>();

    // Default constructor
    public Livestream() {
    }

    // Constructor with fields
    public Livestream(String title, String description, LocalDateTime scheduledAt, Admin admin) {
        this.title = title;
        this.description = description;
        this.scheduledAt = scheduledAt;
        this.admin = admin;
        this.status = LivestreamStatus.SCHEDULED;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LivestreamStatus getStatus() {
        return status;
    }

    public void setStatus(LivestreamStatus status) {
        this.status = status;
    }

    public LocalDateTime getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(LocalDateTime scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public Integer getMaxViewers() {
        return maxViewers;
    }

    public void setMaxViewers(Integer maxViewers) {
        this.maxViewers = maxViewers;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<StreamConfig> getStreamConfigs() {
        return streamConfigs;
    }

    public void setStreamConfigs(List<StreamConfig> streamConfigs) {
        this.streamConfigs = streamConfigs;
    }

    public List<StreamEvent> getStreamEvents() {
        return streamEvents;
    }

    public void setStreamEvents(List<StreamEvent> streamEvents) {
        this.streamEvents = streamEvents;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    public List<Analytics> getAnalytics() {
        return analytics;
    }

    public void setAnalytics(List<Analytics> analytics) {
        this.analytics = analytics;
    }

    public List<LivestreamAccess> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<LivestreamAccess> accesses) {
        this.accesses = accesses;
    }

    // ===== Business methods =====

    public void start() {
        if (this.status == LivestreamStatus.SCHEDULED) {
            this.status = LivestreamStatus.LIVE;
            this.startedAt = LocalDateTime.now();
            addStreamEvent(StreamEventType.STARTED);
        } else {
            throw new IllegalStateException("Cannot start livestream in status: " + this.status);
        }
    }

    public void end() {
        if (this.status == LivestreamStatus.LIVE) {
            this.status = LivestreamStatus.ENDED;
            this.endedAt = LocalDateTime.now();
            addStreamEvent(StreamEventType.STOPPED);
        } else {
            throw new IllegalStateException("Cannot end livestream in status: " + this.status);
        }
    }

    private void addStreamEvent(StreamEventType eventType) {
        StreamEvent event = new StreamEvent();
        event.setEventType(eventType);
        event.setLivestream(this);
        event.setEventTime(LocalDateTime.now());
        this.streamEvents.add(event);
    }

    public boolean isLive() {
        return LivestreamStatus.LIVE.equals(this.status);
    }
}