package com.example.Live.stream.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LivestreamAccessDTO {
    private String id;
    private LocalDateTime joinedAt;
    private LocalDateTime leftAt;

    private String livestreamId;
    private String livestreamTitle;
    private String viewerSessionId;

    // Computed fields
    public String getJoinedAtFormatted() {
        return joinedAt != null ?
                joinedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public String getLeftAtFormatted() {
        return leftAt != null ?
                leftAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }

    public boolean isActive() {
        return leftAt == null;
    }

    public String getWatchDuration() {
        if (joinedAt != null) {
            LocalDateTime end = leftAt != null ? leftAt : LocalDateTime.now();
            long minutes = java.time.Duration.between(joinedAt, end).toMinutes();
            return minutes + " minutes";
        }
        return null;
    }
}