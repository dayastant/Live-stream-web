package com.example.Live.stream.service.dto;

import com.example.Live.stream.domain.enums.LivestreamStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivestreamDTO {
    private String id;
    private String title;
    private String description;
    private LivestreamStatus status;
    private LocalDateTime scheduledAt;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;
    private Integer maxViewers;
    private Integer currentViewers;

    // Relationships
    private String adminId;
    private String adminUsername;

    // Related DTOs
    private List<StreamConfigDTO> streamConfigs;
    private List<StreamEventDTO> streamEvents;
    private List<VideoDTO> videos;
    private List<AnalyticsDTO> analytics;

    // Computed fields
    public String getStatusDisplay() {
        return status != null ? status.name() : null;
    }

    public String getDuration() {
        if (startedAt != null && endedAt != null) {
            long minutes = java.time.Duration.between(startedAt, endedAt).toMinutes();
            return minutes + " minutes";
        }
        return null;
    }

    public boolean isLive() {
        return LivestreamStatus.LIVE.equals(status);
    }

    public String getScheduledAtFormatted() {
        return scheduledAt != null ?
                scheduledAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    }
}