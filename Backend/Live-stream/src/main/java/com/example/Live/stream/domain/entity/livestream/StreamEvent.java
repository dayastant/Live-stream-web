package com.example.Live.stream.domain.entity.livestream;

import com.example.Live.stream.domain.base.BaseEntity;
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

@Entity
@Table(name = "stream_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "livestream")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class StreamEvent extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 20)
    private StreamEventType eventType;

    @Column(name = "event_time", nullable = false)
    @Builder.Default
    private LocalDateTime eventTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    @Column(name = "metadata", length = 500)
    private String metadata; // Additional event data in JSON format

    // Helper method to create event
    public static StreamEvent createEvent(StreamEventType type, Livestream livestream) {
        return StreamEvent.builder()
                .eventType(type)
                .livestream(livestream)
                .build();
    }

    // Business methods
    public boolean isStarted() {
        return StreamEventType.STARTED.equals(this.eventType);
    }

    public boolean isStopped() {
        return StreamEventType.STOPPED.equals(this.eventType);
    }
}