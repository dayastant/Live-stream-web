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
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "livestream")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Analytics extends BaseEntity {

    @Column(name = "concurrent_viewers", nullable = false)
    private Integer concurrentViewers;

    @Column(name = "peak_viewers")
    private Integer peakViewers;

    @Column(name = "recorded_at", nullable = false)
    @Builder.Default
    private LocalDateTime recordedAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livestream_id", nullable = false)
    private Livestream livestream;
}
