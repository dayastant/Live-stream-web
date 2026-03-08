package com.example.Live.stream.domain.entity.viewer;

import com.example.Live.stream.domain.base.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "viewer_sessions")
public class ViewerSession extends BaseEntity {

    @Column(name = "session_token", nullable = false, unique = true)
    private String sessionToken;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "device_info", length = 500)
    private String deviceInfo;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Column(name = "ended_at")
    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "viewerSession", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LivestreamAccess> accesses = new ArrayList<>();

    // Default constructor
    public ViewerSession() {
        this.startedAt = LocalDateTime.now();
    }

    // Constructor with fields
    public ViewerSession(String sessionToken, String ipAddress, String deviceInfo) {
        this.sessionToken = sessionToken;
        this.ipAddress = ipAddress;
        this.deviceInfo = deviceInfo;
        this.startedAt = LocalDateTime.now();
    }

    // ===== Getters and Setters =====

    @Override
    public String getId() {
        return super.getId();
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
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

    public List<LivestreamAccess> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<LivestreamAccess> accesses) {
        this.accesses = accesses;
    }

    // ===== Business methods =====

    public void endSession() {
        this.endedAt = LocalDateTime.now();
    }

    public boolean isActive() {
        return endedAt == null;
    }
}