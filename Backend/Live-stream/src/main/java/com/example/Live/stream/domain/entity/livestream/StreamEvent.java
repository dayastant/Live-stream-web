package com.example.Live.stream.domain.entity.livestream;

import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.enums.StreamEventType;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stream_events")
public class StreamEvent extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private StreamEventType eventType;

    @Column(name = "event_time", nullable = false)
    private LocalDateTime eventTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    // Default constructor
    public StreamEvent() {
        this.eventTime = LocalDateTime.now();
    }

    // Constructor with fields
    public StreamEvent(StreamEventType eventType, Livestream livestream) {
        this.eventType = eventType;
        this.livestream = livestream;
        this.eventTime = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    @Override
    public String getId() {
        return super.getId();
    }

    public StreamEventType getEventType() {
        return eventType;
    }

    public void setEventType(StreamEventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Livestream getLivestream() {
        return livestream;
    }

    public void setLivestream(Livestream livestream) {
        this.livestream = livestream;
    }

    // ===== Business methods =====

    public boolean isStarted() {
        return StreamEventType.STARTED.equals(this.eventType);
    }

    public boolean isStopped() {
        return StreamEventType.STOPPED.equals(this.eventType);
    }
}