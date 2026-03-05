package com.example.Live.stream.domain.entity.viewer;

import com.example.Live.stream.domain.base.BaseEntity;
import com.example.Live.stream.domain.entity.livestream.Livestream;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "livestream_accesses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = {"livestream", "viewerSession"})
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class LivestreamAccess extends BaseEntity {

    @Column(name = "joined_at", nullable = false)
    @Builder.Default
    private LocalDateTime joinedAt = LocalDateTime.now();

    @Column(name = "left_at")
    private LocalDateTime leftAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_session_id", nullable = false)
    private ViewerSession viewerSession;

    // Business methods
    public void leave() {
        this.leftAt = LocalDateTime.now();
    }

    public long getWatchDurationInSeconds() {
        if (leftAt == null) {
            return java.time.Duration.between(joinedAt, LocalDateTime.now()).getSeconds();
        }
        return java.time.Duration.between(joinedAt, leftAt).getSeconds();
    }
}
