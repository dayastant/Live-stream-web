package com.example.Live.stream.domain.entity.livestream;


import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.entity.admin.Admin;
import com.example.Live.stream.domain.entity.viewer.LivestreamAccess;
import com.example.Live.stream.domain.enums.LivestreamStatus;
import com.example.Live.stream.domain.entity.livestream.StreamEvent;


import com.example.Live.stream.domain.enums.StreamEventType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livestreams",
        indexes = {
                @Index(name = "idx_livestream_status", columnList = "status"),
                @Index(name = "idx_livestream_scheduled", columnList = "scheduledAt")
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = {"admin", "streamConfigs", "streamEvents", "videos", "analytics", "accesses"})
public class Livestream extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 1000)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
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
    @Builder.Default
    private List<StreamConfig> streamConfigs = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<StreamEvent> streamEvents = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<Analytics> analytics = new ArrayList<>();

    @OneToMany(mappedBy = "livestream", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private List<LivestreamAccess> accesses = new ArrayList<>();

    // Business methods following Single Responsibility
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
        StreamEvent event = StreamEvent.builder()
                .eventType(eventType)
                .livestream(this)
                .build();
        this.streamEvents.add(event);
    }

    public boolean isLive() {
        return LivestreamStatus.LIVE.equals(this.status);
    }
}