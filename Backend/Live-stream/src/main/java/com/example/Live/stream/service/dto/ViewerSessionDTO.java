package com.example.Live.stream.service.dto;

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
public class ViewerSessionDTO {
    private String id;
    private String sessionToken;
    private String ipAddress;
    private String deviceInfo;
    private LocalDateTime startedAt;
    private LocalDateTime endedAt;

    private List<LivestreamAccessDTO> accesses;

    // Computed fields
    public String getStartedAtFormatted() {
        return startedAt != null ?
                startedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getEndedAtFormatted() {
        return endedAt != null ?
                endedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public boolean isActive() {
        return endedAt == null;
    }

    public String getDuration() {
        if (startedAt != null) {
            LocalDateTime end = endedAt != null ? endedAt : LocalDateTime.now();
            long minutes = java.time.Duration.between(startedAt, end).toMinutes();
            return minutes + " minutes";
        }
        return null;
    }
}