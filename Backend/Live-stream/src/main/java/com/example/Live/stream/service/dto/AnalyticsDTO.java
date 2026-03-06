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
public class AnalyticsDTO {
    private String id;
    private Integer concurrentViewers;
    private Integer peakViewers;
    private LocalDateTime recordedAt;

    private String livestreamId;
    private String livestreamTitle;

    // Computed fields
    public String getRecordedAtFormatted() {
        return recordedAt != null ?
                recordedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null;
    }
}