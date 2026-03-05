package com.example.Live.stream.domain.entity.viewer;


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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viewer_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(callSuper = true, exclude = "accesses")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ViewerSession extends BaseEntity {

    @Column(name = "session_token", nullable = false, unique = true)
    private String sessionToken;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info", length = 500)
    private String deviceInfo;

    @Column(name = "started_at", nullable = false)
    @Builder.Default
    private LocalDateTime startedAt = LocalDateTime.now();

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "viewerSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<LivestreamAccess> accesses = new ArrayList<>();

    // Business methods
    public void endSession() {
        this.endedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return endedAt == null;
    }
}
